package ru.yandex.practicum.gym;

import java.util.*;

public class Main {
    static Timetable timetable = new Timetable();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Введите команду:");
            System.out.println("1 - Добавить тренировку в расписание");
            System.out.println("2 - Вывести все тренировки в конкретное время");
            System.out.println("3 - Вывести все тренировки за определенный день");
            System.out.println("4 - Вывести количество тренировок у каждого тренера");
            System.out.println("0 - Завершить программу");
            int command = scanner.nextInt();
            switch (command) {
                case 1:
                    addNewTrainingSession();
                    break;
                case 2:
                    getTrainingSessionsForDayAndTime();
                    break;
                case 3:
                    getTrainingSessionsForDay();
                    break;
                case 4:
                    timetable.getCountByCoaches();
                    break;
                case 0:
                    System.out.println("Программа завершена!");
                    return;
                default:
                    System.out.println("Неверный ввод!");
                    break;
            }
        }
    }


    static void addNewTrainingSession() {
        System.out.println("Введите номер дня недели:");
        System.out.println("1 - Понедельник");
        System.out.println("2 - Вторник");
        System.out.println("3 - Среда");
        System.out.println("4 - Четверг");
        System.out.println("5 - Пятница");
        System.out.println("6 - Суббота");
        System.out.println("7 - Воскресенье");
        int dayNumber = scanner.nextInt();
        if (dayNumber < 1 || dayNumber > 7) {
            System.out.println("Неверный ввод!");
            return;
        }
        DayOfWeek dayOfWeek;
        switch (dayNumber) {
            case 1:
                dayOfWeek = DayOfWeek.MONDAY;
                break;
            case 2:
                dayOfWeek = DayOfWeek.TUESDAY;
                break;
            case 3:
                dayOfWeek = DayOfWeek.WEDNESDAY;
                break;
            case 4:
                dayOfWeek = DayOfWeek.THURSDAY;
                break;
            case 5:
                dayOfWeek = DayOfWeek.FRIDAY;
                break;
            case 6:
                dayOfWeek = DayOfWeek.SATURDAY;
                break;
            case 7:
                dayOfWeek = DayOfWeek.SUNDAY;
                break;
            default:
                System.out.println("Неверный ввод!");
                return;
        }

        System.out.println("Введите время занятий");
        System.out.println("Часы: ");
        int hours = scanner.nextInt();
        if (hours < 0 || hours > 23) {
            System.out.println("Неверный ввод!");
            return;
        }
        System.out.println("Минуты: ");
        int minutes = scanner.nextInt();
        if (minutes < 0 || minutes > 59) {
            System.out.println("Неверный ввод!");
            return;
        }
        TimeOfDay timeOfDay = new TimeOfDay(hours, minutes);

        System.out.println("Название группы: ");
        scanner.nextLine();
        String title = scanner.nextLine();

        System.out.println("Введите номер группы:");
        System.out.println("1 - Группа для детей");
        System.out.println("2 - Группа для взрослых");
        int groupNumber = scanner.nextInt();
        Age age;
        switch (groupNumber) {
            case 1:
                age = Age.CHILD;
                break;
            case 2:
                age = Age.ADULT;
                break;
            default:
                System.out.println("Неверный ввод!");
                return;
        }

        System.out.println("Продолжительность тренировки: ");
        int duration = scanner.nextInt();

        Group group = new Group(title, age, duration);

        System.out.println("Введите ФИО тренера: ");

        String surname = scanner.next();
        String name = scanner.next();
        String middleName = scanner.next();

        Coach coach = new Coach(surname, name, middleName);

        TrainingSession trainingSession = new TrainingSession(group, coach, dayOfWeek, timeOfDay);

        timetable.addNewTrainingSession(trainingSession);
    }

    static void getTrainingSessionsForDay() {
        if (timetable.getTimetable().isEmpty()) {
            System.out.println("Вы не добавили ни одной тренировки!");
            return;
        }

        System.out.println("Введите номер дня недели:");
        System.out.println("1 - Понедельник");
        System.out.println("2 - Вторник");
        System.out.println("3 - Среда");
        System.out.println("4 - Четверг");
        System.out.println("5 - Пятница");
        System.out.println("6 - Суббота");
        System.out.println("7 - Воскресенье");
        int dayNumber = scanner.nextInt();
        if (dayNumber < 1 || dayNumber > 7) {
            System.out.println("Неверный ввод!");
            return;
        }
        DayOfWeek dayOfWeek;
        switch (dayNumber) {
            case 1:
                dayOfWeek = DayOfWeek.MONDAY;
                break;
            case 2:
                dayOfWeek = DayOfWeek.TUESDAY;
                break;
            case 3:
                dayOfWeek = DayOfWeek.WEDNESDAY;
                break;
            case 4:
                dayOfWeek = DayOfWeek.THURSDAY;
                break;
            case 5:
                dayOfWeek = DayOfWeek.FRIDAY;
                break;
            case 6:
                dayOfWeek = DayOfWeek.SATURDAY;
                break;
            case 7:
                dayOfWeek = DayOfWeek.SUNDAY;
                break;
            default:
                System.out.println("Неверный ввод!");
                return;
        }

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingsOfTime = timetable.getTrainingSessionsForDay(dayOfWeek);
        if (trainingsOfTime == null) {
            return;
        }
        for (ArrayList<TrainingSession> listOfTrainings: trainingsOfTime.values()) {
            for (int i = 0; i < listOfTrainings.size(); i++) {
                System.out.println(listOfTrainings.get(i));
            }
        }
    }

    static void getTrainingSessionsForDayAndTime() {
        if (timetable.getTimetable().isEmpty()) {
            System.out.println("Вы не добавили ни одной тренировки!");
            return;
        }
        System.out.println("Введите номер дня недели:");
        System.out.println("1 - Понедельник");
        System.out.println("2 - Вторник");
        System.out.println("3 - Среда");
        System.out.println("4 - Четверг");
        System.out.println("5 - Пятница");
        System.out.println("6 - Суббота");
        System.out.println("7 - Воскресенье");
        int dayNumber = scanner.nextInt();
        if (dayNumber < 1 || dayNumber > 7) {
            System.out.println("Неверный ввод!");
            return;
        }
        DayOfWeek dayOfWeek;
        switch (dayNumber) {
            case 1:
                dayOfWeek = DayOfWeek.MONDAY;
                break;
            case 2:
                dayOfWeek = DayOfWeek.TUESDAY;
                break;
            case 3:
                dayOfWeek = DayOfWeek.WEDNESDAY;
                break;
            case 4:
                dayOfWeek = DayOfWeek.THURSDAY;
                break;
            case 5:
                dayOfWeek = DayOfWeek.FRIDAY;
                break;
            case 6:
                dayOfWeek = DayOfWeek.SATURDAY;
                break;
            case 7:
                dayOfWeek = DayOfWeek.SUNDAY;
                break;
            default:
                System.out.println("Неверный ввод!");
                return;
        }

        System.out.println("Введите время занятий");
        System.out.println("Часы: ");
        int hours = scanner.nextInt();
        if (hours < 0 || hours > 23) {
            System.out.println("Неверный ввод!");
            return;
        }
        System.out.println("Минуты: ");
        int minutes = scanner.nextInt();
        if (minutes < 0 || minutes > 59) {
            System.out.println("Неверный ввод!");
            return;
        }
        TimeOfDay timeOfDay = new TimeOfDay(hours, minutes);
        ArrayList<TrainingSession> listOfTrainings = timetable.getTrainingSessionsForDayAndTime(dayOfWeek,timeOfDay);
        if (listOfTrainings == null) {
            return;
        }
        for (int i = 0; i < listOfTrainings.size(); i++) {
            System.out.println(listOfTrainings.get(i));
        }
    }
}

