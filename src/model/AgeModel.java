package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class AgeModel {

    private static final double CUPS_OF_COFFEE_PER_DAY = 2.0;
    private static final double TV_EPISODES_PER_DAY = 3.0;
    private static final double SOCIAL_FEED_KM_PER_DAY = 0.1;

    private static final String PREF_LAST_INPUT = "lastBirthDate";

    private LocalDate birthDate;
    private final Preferences prefs = Preferences.userNodeForPackage(AgeModel.class);
    private final List<ModelListener> listeners = new ArrayList<>();

    public void addListener(ModelListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ModelListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (ModelListener listener : listeners) {
            listener.onModelChanged();
        }
    }

    public void setBirthDate(int day, int month, int year) {
        if (year < 1900 || year > LocalDate.now().getYear()) {
            throw new IllegalArgumentException(
                    "Year must be between 1900 and " + LocalDate.now().getYear() + ".");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12.");
        }
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Day must be between 1 and 31.");
        }

        LocalDate date;
        try {
            date = LocalDate.of(year, month, day);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Such date does not exist: " + day + "." + month + "." + year);
        }

        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birthday can not be in future.");
        }

        this.birthDate = date;
        saveLastInput();
        notifyListeners();
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public boolean restoreLastInput() {
        String saved = prefs.get(PREF_LAST_INPUT, null);
        if (saved == null) return false;
        try {
            this.birthDate = LocalDate.parse(saved, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private void saveLastInput() {
        if (birthDate != null) {
            prefs.put(PREF_LAST_INPUT, birthDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }

    public int getYears() {
        checkDate();
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public int getMonths() {
        checkDate();
        return Period.between(birthDate, LocalDate.now()).getMonths();
    }

    public int getDays() {
        checkDate();
        return Period.between(birthDate, LocalDate.now()).getDays();
    }

    public long getTotalDays() {
        checkDate();
        return ChronoUnit.DAYS.between(birthDate, LocalDate.now());
    }

    public long getTotalMinutes() {
        checkDate();
        LocalDateTime birth = birthDate.atStartOfDay();
        return ChronoUnit.MINUTES.between(birth, LocalDateTime.now());
    }

    public double getCupsOfCoffee() {
        return getTotalDays() * CUPS_OF_COFFEE_PER_DAY;
    }

    public double getTvEpisodes() {
        return getTotalDays() * TV_EPISODES_PER_DAY;
    }

    public double getSocialFeedKm() {
        return getTotalDays() * SOCIAL_FEED_KM_PER_DAY;
    }

    private void checkDate() {
        if (birthDate == null) {
            throw new IllegalStateException("Date of birth is not entered.");
        }
    }
}