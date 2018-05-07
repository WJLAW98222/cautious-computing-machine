import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.text.ParseException;
import java.util.List;


/*
* You need to implement Calendar GUI here!
* show the calendar of month of today.
* jump to last/next month's calendar
* jump to last/next year's calendar
*
* jump to one specific day's calendar
* */
public class Display extends Application {


    private GridPane daysPane = new GridPane();
    private Button[][] daysBtn = new Button[6][7];
    private String[][] days;
    private EventHold eventHold = new EventHold();
    private int currentYear = DateUtil.getToday().getYear();
    private int currentMonth = DateUtil.getToday().getMonth();
    private VBox eventDisplayByDay = new VBox();
    private VBox eventDisplayByTimes = new VBox();
    private ComboBox<Integer> years;
    private ComboBox<Integer> months;

    public Display() {
    }

    //启动界面的方法
    public static void play() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox columnPane = new HBox();
        HBox datePane = new HBox();
        GridPane weekPane = new GridPane();
        VBox hintPane = new VBox();
        HBox searchPane = new HBox();
        VBox queryPane = new VBox();
        VBox addPane = new VBox();
        VBox leftPane = new VBox();
        leftPane.getChildren().addAll(datePane, weekPane, daysPane, hintPane, searchPane);
        columnPane.getChildren().addAll(leftPane, addPane, queryPane);
        columnPane.setSpacing(15);
        Label displayYear = new Label("年份：");
        Label displayMonth = new Label("月份：");
        Label hintText = new Label("请输入“2018-1-1”或“2018-01-01”格式的日期，" + "\n" +
                "本日历系统只支持1800-1-1至2300-12-31之间的日期" + "\n" + "请输入正确格式和有效的日期!");
        Button lookBtn = new Button("查看");
        Button todayBtn = new Button("今天");
        Button searchBtn = new Button("查询");

        years = new ComboBox<>();
        months = new ComboBox<>();
        years.setValue(DateUtil.getToday().getYear());
        months.setValue(DateUtil.getToday().getMonth());
        years.getItems().addAll(createDownTable(1800, 501));
        months.getItems().addAll(createDownTable(1, 12));
        TextField searchText = new TextField();
        String[] week = {"日", "一", "二", "三", "四", "五", "六"};
        for (int i = 0; i < week.length; i++) {
            weekPane.add(new Label(week[i]), i, 0);
        }
        datePane.setAlignment(Pos.CENTER);
        datePane.setPadding(new Insets(15, 15, 15, 15));
        datePane.setSpacing(5);
        weekPane.setHgap(27);
        weekPane.setPadding(new Insets(15, 15, 15, 30));
        daysPane.setHgap(7);
        daysPane.setVgap(5);
        daysPane.setPadding(new Insets(15, 15, 15, 25));

        searchPane.setAlignment(Pos.CENTER);
        searchPane.setSpacing(5);
        searchPane.setPadding(new Insets(15, 15, 15, 15));
        datePane.getChildren().addAll(displayYear, years, displayMonth, months, lookBtn, todayBtn);
        searchPane.getChildren().addAll(searchText, searchBtn);
        hintPane.getChildren().add(hintText);
        hintPane.setAlignment(Pos.CENTER);
        hintPane.setSpacing(5);
        hintPane.setPadding(new Insets(15, 15, 15, 15));

        Label addLabel = new Label("添加");
        Label addByDayLabel = new Label("按天添加（搜索格式同搜索日期一致）");
        Label searchByDayLabel = new Label("时间");
        TextField searchByDay = new TextField();
        Label describeLabel_1 = new Label("事件描述");
        TextField describe_1 = new TextField();
        HBox addByDayPane_1 = new HBox();
        HBox addByDayPane_2 = new HBox();
        addByDayPane_1.setSpacing(15);
        addByDayPane_2.setSpacing(15);
        addByDayPane_1.getChildren().addAll(searchByDayLabel, searchByDay);
        addByDayPane_2.getChildren().addAll(describeLabel_1, describe_1);
        Button addBtn_1 = new Button("添加");
        Label searchByTimesLabel = new Label("按时间段添加（搜索格式年月日同搜索日期一致，小时分钟为24小时制，"
                + "\n" + "例2018-04-14-23:59）（冒号为英文冒号）");
        Label start_1 = new Label("起始时间");
        TextField startTime_1 = new TextField();
        Label end_1 = new Label("结束时间");
        TextField endTime_1 = new TextField();
        HBox searchByTimesPane1 = new HBox();
        HBox searchByTimesPane2 = new HBox();
        searchByTimesPane1.setSpacing(15);
        searchByTimesPane2.setSpacing(15);
        searchByTimesPane1.getChildren().addAll(start_1, startTime_1);
        searchByTimesPane2.getChildren().addAll(end_1, endTime_1);
        Label describeLabel_2 = new Label("事件描述");
        TextField describe_2 = new TextField();
        HBox addByTimePane = new HBox();
        addByTimePane.setSpacing(15);
        addByTimePane.getChildren().addAll(describeLabel_2, describe_2);
        Button addBtn_2 = new Button("添加");
        VBox addPane_1 = new VBox();
        VBox addPane_2 = new VBox();
        VBox addPane_3 = new VBox();
        addPane_1.getChildren().add(addLabel);
        addPane_2.getChildren().addAll(addByDayLabel, addByDayPane_1, addByDayPane_2, addBtn_1);
        addPane_3.getChildren().addAll(searchByTimesLabel, searchByTimesPane1, searchByTimesPane2, addByTimePane, addBtn_2);
        addPane.getChildren().addAll(addPane_1, addPane_2, addPane_3);
        paneSet(addPane);
        paneSet(addPane_1);
        paneSet(addPane_2);
        paneSet(addPane_3);

        Label queryLabel = new Label("查询");
        Label queryByDayLabel = new Label("点击某天查询");

        Label queryByTimeLabel = new Label("按时间段查询，搜索输入方式同添加一致");
        Label queryStart = new Label("起始时间");
        TextField queryStartText = new TextField();
        Label queryEnd = new Label("结束时间");
        TextField queryEndText = new TextField();
        HBox queryByTimesPane1 = new HBox();
        HBox queryByTimesPane2 = new HBox();
        queryByTimesPane1.setSpacing(15);
        queryByTimesPane2.setSpacing(15);
        queryByTimesPane1.getChildren().addAll(queryStart, queryStartText);
        queryByTimesPane2.getChildren().addAll(queryEnd, queryEndText);
        Button queryBtn = new Button("查询");

        VBox queryPane_1 = new VBox();
        VBox queryPane_2 = new VBox();
        VBox queryPane_3 = new VBox();
        queryPane_1.getChildren().add(queryLabel);
        queryPane_2.getChildren().addAll(queryByDayLabel, eventDisplayByDay);
        queryPane_3.getChildren().addAll(queryByTimeLabel, queryByTimesPane1, queryByTimesPane2, queryBtn, eventDisplayByTimes);
        queryPane.getChildren().addAll(queryPane_1, queryPane_2, queryPane_3);
        paneSet(queryPane);
        paneSet(queryPane_1);
        paneSet(queryPane_2);
        paneSet(queryPane_3);
        eventDisplayByDay.setSpacing(15);
        eventDisplayByTimes.setSpacing(15);

        paintDays(DateUtil.getToday());
        lookHandler(lookBtn);
        getTodayHandler(todayBtn);

        searchBtn.setOnAction(e -> {
            timeSearchHandler(searchText);
        });
        addBtn_1.setOnAction(e -> {
            try {
                addByDay(searchByDay, describe_1);
            } catch (DateCreateException e1) {
                e1.printStackTrace();
            }
        });
        addBtn_2.setOnAction(e -> {
            try {
                try {
                    addByTime(startTime_1, endTime_1, describe_2);
                } catch (DateCreateException e1) {
                    e1.printStackTrace();
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        });
        queryBtn.setOnAction(e -> {
            try {
                try {
                    queryByTime(queryStartText, queryEndText);
                } catch (DateCreateException e1) {
                    e1.printStackTrace();
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        });

        Scene scene = new Scene(columnPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("日历");
        primaryStage.show();
    }

    //解决按钮显示今日日期的事件处理；
    private void getTodayHandler(Button btn) {
        btn.setOnAction(e -> {
            years.setValue(DateUtil.getToday().getYear());
            months.setValue(DateUtil.getToday().getMonth());
            paintDays(DateUtil.getToday());
        });
    }

    //解决按钮查看选定月份的事件处理
    private void lookHandler(Button btn) {
        btn.setOnAction(e -> {
            int year = years.getValue();
            int month = months.getValue();
            CalendarDate calendarDate = new CalendarDate(year, month, 1);
            paintDays(calendarDate);
        });
    }

    //打印当前月份所有天数，并且处理当天日期显色为红色，有事件日期显示为蓝色
    private void paintDays(CalendarDate date) {
        if (date == null) {
            return;
        }
        currentYear = date.getYear();
        currentMonth = date.getMonth();
        days = DateUtil.getDays(date);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                daysBtn[i][j] = new Button(days[i][j]);
                Button button = new Button(days[i][j]);
                daysBtn[i][j].setOnAction(e -> {
                    queryByDay(button);
                });
                daysPane.add(daysBtn[i][j], j, i);
                GridPane.setHalignment(daysBtn[i][j], HPos.CENTER);
                daysBtn[i][j].setMaxWidth(40);
            }
        }
        List<EventNode> eventNodes = eventHold.getAllEvents();
        List<CalendarDate> eventDate = DateUtil.getCalendarDateFromEvents(eventNodes);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                daysBtn[i][j].setTextFill(Color.BLACK);
                if (!days[i][j].equals("  ")) {
                    CalendarDate calendarDate = new CalendarDate(date.getYear(), date.getMonth(), Integer.parseInt(days[i][j]));
                    if (calendarDate.isTheSameDay(DateUtil.getToday())) {
                        this.daysBtn[i][j].setTextFill(Color.RED);
                    }
                    if (eventDate != null) {
                        for (int k = 0; k < eventDate.size(); k++) {
                            if (calendarDate.isTheSameDay(eventDate.get(k))) {
                                this.daysBtn[i][j].setTextFill(Color.BLUE);
                            }
                        }
                    }
                }
            }
        }

        years.setValue(date.getYear());
        months.setValue(date.getMonth());
    }

    //解决显示事件，每次要显示事件，会将两个展示pane里的节点全部去掉
    private void paintEvents(List<EventNode> eventNodes, VBox displayPane) {
        if (eventNodes == null || eventNodes.size() == 0) {
            return;
        }

        cleanPane(eventDisplayByDay);
        cleanPane(eventDisplayByTimes);

        for (int i = 0; i < eventNodes.size(); i++) {
            displayPane.getChildren().add(getEventPane(eventNodes.get(i)));
        }
    }

    //这个方法用于产生两个下拉列表的数据，输入下拉列表开始的数begin,一共有多少个数number
    private ObservableList<Integer> createDownTable(int begin, int number) {
        Integer[] array = new Integer[number];
        for (int i = 0; i < array.length; i++) {
            array[i] = begin;
            begin++;
        }
        ObservableList<Integer> items = FXCollections.observableArrayList(array);
        return items;
    }

    //用于构造显示的事件的小的pane
    private HBox getEventPane(EventNode event) {
        if (event == null) {
            return null;
        }

        HBox eventPane = new HBox();
        Label time = new Label("");
        if (event.getTimes() != null) {
            String timeString = DateUtil.changeDateWithHMToString(event.getTimes().get(0)) + "\n" + "至"
                    + DateUtil.changeDateWithHMToString(event.getTimes().get(event.getTimes().size() - 1));
            time.setText(timeString);
        } else if (event.getTime() != null) {
            String timeString = DateUtil.changeCalendarDateToString(event.getTime());
            time.setText(timeString);
        }
        Label thing = new Label(event.getEvent());
        Button deleteBtn = new Button("删除");

        eventPane.getChildren().addAll(time, thing, deleteBtn);
        eventPane.setSpacing(20);
        deleteBtn.setOnAction(e -> {
            removeEvent(deleteBtn, event);
        });
        return eventPane;
    }

    //对界面数值的部分设定
    private void paneSet(VBox pane) {
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(15);
        pane.setPadding(new Insets(15, 15, 15, 15));
    }

    //根据hint的数字得到提示内容并用新的界面提醒
    private void setNewStage(int hint) {
        if (getHintFromNumber(hint) == null) {
            return;
        }
        Stage stage = new Stage();
        Label hintLabel = new Label(getHintFromNumber(hint));
        Button OKBtn = new Button("知道了");
        OKBtn.setOnAction(e -> {
            stage.close();
        });
        VBox pane = new VBox();
        pane.getChildren().addAll(hintLabel, OKBtn);
        pane.setSpacing(15);
        pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(pane, 200, 100);
        stage.setScene(scene);
        stage.setTitle("提示");
        stage.show();
    }

    /*根据数字返回提示内容，
    * 0，1,2,3,4,5,6,7,8,9分别对应于数组hints的内容
     */
    private String getHintFromNumber(int hint) {
        if (hint < 0 || hint > 9)
            return null;
        String[] hints = {"搜索成功！", "请输入有效的日期！", "请输入范围内的日期！", "请输入正确格式的日期！",
                "事件描述不能为空！", "起始日期不能晚于结束日期！", "添加事件成功！", "删除事件成功！",
                "该时间内没有事件！", "请勿重复删除事件！"};
        return hints[hint];
    }

    //用于处理解决搜索时间的处理方法
    private void timeSearchHandler(TextField searchText) {
        String input = searchText.getText();
        int hint = 0;
        if (!DateUtil.isFormatted(input)) {
            hint = 3;
        } else {
            CalendarDate calendarDate = null;
            try {
                calendarDate = new CalendarDate(input);
            } catch (DateCreateException e) {
                e.printStackTrace();
            }
            if (!DateUtil.isValid(calendarDate)) {
                hint = 1;
            } else if (calendarDate.getYear() > 2300 || calendarDate.getYear() < 1800) {
                hint = 2;
            } else {
                years.setValue(calendarDate.getYear());
                months.setValue(calendarDate.getMonth());
                paintDays(calendarDate);
            }
        }
        setNewStage(hint);
    }

    //用于处理按天添加事件的处理方法，添加成功会跳到该日期
    private void addByDay(TextField time, TextField describe) throws DateCreateException {
        String dateString = time.getText();
        String eventDescribe = describe.getText();
        int hint = 6;
        if (dateString.equals("")) {
            hint = 3;
        } else if (eventDescribe.equals("")) {
            hint = 4;
        } else if (!DateUtil.isFormatted(dateString)) {
            hint = 3;
        } else {
            CalendarDate calendarDate = new CalendarDate(dateString);
            if (!DateUtil.isValid(calendarDate)) {
                hint = 1;
            } else if (calendarDate.getYear() < 1800 || calendarDate.getYear() > 2300) {
                hint = 2;
            } else {
                EventNode eventNode = new EventNode(calendarDate, eventDescribe);
                eventHold.addEvent(eventNode);
                paintDays(calendarDate);
            }
        }
        setNewStage(hint);
    }

    //用于处理按时间段添加的处理方法，添加成功会跳到该时间段开始的日期所在月份
    private void addByTime(TextField start, TextField end, TextField describe) throws ParseException, DateCreateException {
        String startString = start.getText();
        String endString = end.getText();
        String eventDescribe = describe.getText();
        int hint = 6;
        if (startString.equals("") || endString.equals("")) {
            hint = 3;
        } else if (eventDescribe.equals("")) {
            hint = 4;
        } else if (!DateUtil.isFormattedTime(startString) || !DateUtil.isFormattedTime(endString)) {
            hint = 3;
        } else {
            DateWithHM startDate = new DateWithHM(startString);
            DateWithHM endDate = new DateWithHM(endString);
            if (!DateUtil.isValidTime(startDate) || !DateUtil.isValidTime(endDate)) {
                hint = 1;
            } else if (!DateUtil.isProperTime(startDate, endDate)) {
                hint = 5;
            } else if (startDate.getYear() < 1800 || startDate.getYear() > 2300 || endDate.getYear() < 1800 || endDate.getYear() > 2300) {
                hint = 2;
            } else {
                List<DateWithHM> list = DateUtil.getDaysFromTimes(startDate, endDate);
                EventNode eventNode = new EventNode(list, eventDescribe);
                eventHold.addEvent(eventNode);
                paintDays(new CalendarDate(startDate.getYear(), startDate.getMonth(), 1));
            }
        }
        setNewStage(hint);
    }

    //用于处理按时间段查询的处理方法
    private void queryByTime(TextField start, TextField end) throws ParseException, DateCreateException {
        String startString = start.getText();
        String endString = end.getText();
        int hint = 0;
        if (startString.equals("") || endString.equals("")) {
            hint = 3;
        } else if (!DateUtil.isFormattedTime(startString) || !DateUtil.isFormattedTime(endString)) {
            hint = 3;
        } else {
            DateWithHM startDate = new DateWithHM(startString);
            DateWithHM endDate = new DateWithHM(endString);
            if (!DateUtil.isValidTime(startDate) || !DateUtil.isValidTime(endDate)) {
                hint = 1;
            } else if (!DateUtil.isProperTime(startDate, endDate)) {
                hint = 5;
            } else {
                List<DateWithHM> list = DateUtil.getDaysFromTimes(startDate, endDate);
                List<EventNode> eventNodes = eventHold.getEvents(list);
                if (eventNodes == null) {
                    hint = 8;
                } else {
                    paintEvents(eventNodes, eventDisplayByTimes);
                }
            }
        }
        setNewStage(hint);
    }

    //解决按天查询的处理办法
    private void queryByDay(Button button) {
        String dayString = button.getText();
        if (dayString.equals("  ")) {
            return;
        }
        int day = Integer.parseInt(dayString);
        CalendarDate date = new CalendarDate(currentYear, currentMonth, day);
        List<EventNode> list = eventHold.getEvent(date);
        if (list == null) {
            setNewStage(8);
        } else {
            paintEvents(list, eventDisplayByDay);
        }
    }

    //解决删除事件
    private void removeEvent(Button deleteBtn, EventNode event) {
        if (deleteBtn.getText().equals("删除")) {
            deleteBtn.setText("已删除");
            event.setExist(false);
            paintDays(new CalendarDate(currentYear, currentMonth, 1));
            setNewStage(7);
        } else {
            setNewStage(9);
        }
    }

    //清理掉pane中所有节点
    private void cleanPane(VBox displayPane) {
        int size = displayPane.getChildren().size();
        if (size != 0) {
            if (size == 1) {
                displayPane.getChildren().remove(0);
            } else {
                displayPane.getChildren().remove(0, size);
            }
        }
    }

}
