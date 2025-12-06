package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();


    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        /*
        Сперва в отдельные переменные вносим день недели и время тренировки (переменные day и time),
        создаем переменную trainingsOfTime, которая хранит таблицу (TreeMap) со списками занятий, если такой таблицы нет,
        значит создаем.  Аналогично делаем со списком занятий listOfTrainings. После чего добавляем тренировку
        в лист listOfTraining, а лист добавляем в trainingsOfTime, a trainingsOfTime добавляем в HashMap timetable.
         */
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingsOfTime = timetable.get(day);
        if (trainingsOfTime == null) {
            trainingsOfTime = new TreeMap<>();
        }
        ArrayList<TrainingSession> listOfTrainings = trainingsOfTime.get(time);
        if (listOfTrainings == null) {
            listOfTrainings = new ArrayList<>();
        }
        listOfTrainings.add(trainingSession);
        trainingsOfTime.put(time, listOfTrainings);
        timetable.put(day, trainingsOfTime);
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

    public ArrayList<Coach> getCountByCoaches() { // метод для подсчета количества тренировок каждого тренера
        /*
        Сперва в методе переопределяется метод интерфейса Comparator, он нужен для сортировки тренеров по количеству
        тренировок. Далее создаем список тренеров и проходимся по всей хэш таблице timetable,
        занося уникальных тренеров в список coaches. После чего заново проходимся по хэш таблице и увеличиваем счетчик
        у тех тренеров в списке coaches, чьи имена повторяются в хэш таблице timetable. Сортируем список coaches при
        помощи sort, выводим имена тренеров и количество их тренировок.
         */

        Comparator<Coach> comparatorOfCoaches = (Coach coach1, Coach coach2) -> {
            return coach2.getCountOfTrainings() - coach1.getCountOfTrainings();
        };

        ArrayList<Coach> coaches = new ArrayList<>();

        for (TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingsOfTime : timetable.values()) {
            for (ArrayList<TrainingSession> listOfTraining : trainingsOfTime.values()) {
                for (int i = 0; i < listOfTraining.size(); i++) {
                    if (!(coaches.contains(listOfTraining.get(i).getCoach()))) {
                        String surname = listOfTraining.get(i).getCoach().getSurname();
                        String name = listOfTraining.get(i).getCoach().getName();
                        String middleName = listOfTraining.get(i).getCoach().getMiddleName();
                        coaches.add(new Coach(surname, name, middleName));
                    }
                }
            }
        }

        if (coaches.isEmpty()) {
            System.out.println("Вы не добавили ни одной тренировки!");
            return null;
        }


        for (TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingsOfTime : timetable.values()) {
            for (ArrayList<TrainingSession> listOfTraining : trainingsOfTime.values()) {
                for (int i = 0; i < listOfTraining.size(); i++) {
                    if (coaches.contains(listOfTraining.get(i).getCoach())) {
                        int index = coaches.indexOf(listOfTraining.get(i).getCoach());
                        coaches.get(index).increaseCount();
                    }
                }
            }
        }

        coaches.sort(comparatorOfCoaches); // сортируем список coaches

        for (int i = 0; i < coaches.size(); i++) {
            System.out.println("Тренер: " + coaches.get(i).getSurname() + " " + coaches.get(i).getName() + " "
                    + coaches.get(i).getMiddleName() + ", количество тренировок: " + coaches.get(i).getCountOfTrainings());
        }
        return coaches;
    }

    public HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> getTimetable() {
        return timetable;
    }
}
