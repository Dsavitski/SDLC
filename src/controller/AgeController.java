package controller;

import model.AgeModel;
import model.ModelListener;
import view.AgeView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AgeController implements ModelListener {

    private final AgeModel model;
    private final AgeView view;

    public AgeController(AgeModel model, AgeView view) {
        this.model = model;
        this.view = view;
        model.addListener(this);
        bind();
    }

    private void bind() {
        view.getBtnEnter().addActionListener(e -> {
            if (model.restoreLastInput()) {
                LocalDate d = model.getBirthDate();
                view.fillDialog(
                        String.valueOf(d.getDayOfMonth()),
                        String.valueOf(d.getMonthValue()),
                        String.valueOf(d.getYear()));
            } else {
                view.clearDialog();
            }
            view.getInputDialog().setVisible(true);
        });

        view.getBtnOk().addActionListener(e -> onOk());

        view.getBtnCancel().addActionListener(e ->
                view.getInputDialog().setVisible(false));
    }

    private void onOk() {
        int day, month, year;
        try {
            day = Integer.parseInt(view.getTfDay().getText().trim());
            month = Integer.parseInt(view.getTfMonth().getText().trim());
            year = Integer.parseInt(view.getTfYear().getText().trim());
        } catch (NumberFormatException ex) {
            view.showError("All fields must contain int values.");
            return;
        }

        try {
            model.setBirthDate(day, month, year);
        } catch (IllegalArgumentException ex) {
            view.showError(ex.getMessage());
        }

        view.getInputDialog().setVisible(false);
    }

    @Override
    public void onModelChanged() {
        displayResults();
    }

    private void displayResults() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);

        String result = String.format(
                "Birth date: %s%n%n" +
                        "Age: %d years, %d months %d days.%n" +
                        "Living days: %,d%n" +
                        "Living minutes: %,d%n%n" +
                        "— Cups of coffee are drunk:          %,.0f%n" +
                        "— The number of episodes watched:   %,.0f%n" +
                        "— Km of social media scrolled: %,.1f",
                model.getBirthDate().format(df),
                model.getYears(), model.getMonths(), model.getDays(),
                model.getTotalDays(),
                model.getTotalMinutes(),
                model.getCupsOfCoffee(),
                model.getTvEpisodes(),
                model.getSocialFeedKm()
        );
        view.showResult(result);
    }
}