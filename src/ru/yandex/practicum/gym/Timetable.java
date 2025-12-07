package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();


    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        /*
        Сперва в отдельные переменные вносим день недели и время тренировки (переменные day и time),
        создаем переменную trainingsForDay, которая хранит таблицу (TreeMap) со списками занятий, если такой таблицы нет,
        значит создаем.  Аналогично делаем со списком занятий listOfTrainings. После чего добавляем тренировку
        в лист listOfTraining, а лист добавляем в trainingsForDay, a trainingsForDay добавляем в HashMap timetable.
         */
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingsForDay = timetable.get(day);
        if (trainingsForDay == null) {
            trainingsForDay = new TreeMap<>();
            timetable.put(day, trainingsForDay);
        }
        ArrayList<TrainingSession> listOfTrainings = trainingsForDay.get(time);
        if (listOfTrainings == null) {
            listOfTrainings = new ArrayList<>();
            trainingsForDay.put(time, listOfTrainings);
        }
        listOfTrainings.add(trainingSession);
        System.out.println("Тренировка добавлена!");
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if (timetable.get(dayOfWeek) == null) {
            System.out.println("В этот день нет тренировок!");
            return null;
        } else return timetable.get(dayOfWeek);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if (timetable.get(dayOfWeek) == null) {
            System.out.println("В этот день нет тренировок!");
            return null;
        } else if (timetable.get(dayOfWeek).get(timeOfDay) == null) {
            System.out.println("В это время нет тренировок!");
            return null;
        } else return timetable.get(dayOfWeek).get(timeOfDay);
    }

    public ArrayList<CounterOfTrainings> getCountByCoaches() {
        // метод для подсчета количества тренировок каждого тренера
        Comparator<CounterOfTrainings> comparator = (counter1, counter2) -> {
            return counter2.getCount() - counter1.getCount();
        };

        HashMap<Coach, Integer> coaches = new HashMap<>();
        ArrayList<CounterOfTrainings> listOfCoaches = new ArrayList<>();
        for (TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingsOfTime : timetable.values()) {
            for (ArrayList<TrainingSession> listOfTraining : trainingsOfTime.values()) {
                for (int i = 0; i < listOfTraining.size(); i++) {
                    Coach coach = listOfTraining.get(i).getCoach();
                    if (!coaches.containsKey(coach)) {
                        coaches.put(coach, 1);
                    } else {
                        coaches.put(coach, coaches.get(coach) + 1);
                    }
                }
            }
        }

        if (coaches.isEmpty()) {
            System.out.println("Вы не добавили ни одной тренировки!");
            return null;
        }

        for (Map.Entry<Coach, Integer> coach : coaches.entrySet()) {
            listOfCoaches.add(new CounterOfTrainings(coach.getKey(), coach.getValue()));
        }

        listOfCoaches.sort(comparator);

        for (int i = 0; i < listOfCoaches.size(); i++) {
            System.out.println("Тренер: " + listOfCoaches.get(i).getCoach().getSurname() + " "
                    + listOfCoaches.get(i).getCoach().getName()
                    + " " + listOfCoaches.get(i).getCoach().getMiddleName() + ", количество тренировок: "
                    + listOfCoaches.get(i).getCount());
        }
        return listOfCoaches;

    }

    public HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> getTimetable() {
        return timetable;
    }
}
