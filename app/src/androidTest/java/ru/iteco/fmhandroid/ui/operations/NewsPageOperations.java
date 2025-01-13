package ru.iteco.fmhandroid.ui.operations;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

import android.widget.DatePicker;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;

import org.hamcrest.Matchers;

import ru.iteco.fmhandroid.ui.pages.AndroidElementLocators;
import ru.iteco.fmhandroid.ui.pages.NewsPageLocators;

public class NewsPageOperations {


    ControlElements controlElements = new ControlElements();

    //выбор категории
    public void pickNewsCategory(int category) {
        controlElements.pickButtonOut(NewsPageLocators.NEWS_ITEM_CATEGORY_TEXT_AUTO_COMPLETE_TEXT_VIEW);
        DataInteraction materialTextView = onData(anything())
                .inRoot(isPlatformPopup()).atPosition(category);
        materialTextView.perform(click());
    }

    //фильтр по категории
    public void filterNewsByCategory(int category) {
        controlElements.pickButtonOut(NewsPageLocators.FILTER_NEWS_MATERIAL_BUTTON);
        pickNewsCategory(category);
        controlElements.pickButtonOut(NewsPageLocators.FILTER_BUTTON);
    }

    //ввод текста (для заголовков и дискрипшенов)
    public void textInput(int placeToInputText, String text) {
        ViewInteraction textInputEditText = onView(
                allOf(withId(placeToInputText),
                        isDisplayed()));
        textInputEditText.perform(replaceText(text), closeSoftKeyboard());
    }

    //установка времени
    public void setDateByDataPicker(int datePickerLaunchViewId, int year, int month, int day) {
        controlElements.pickButtonOut(datePickerLaunchViewId);
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month, day));
        onView(withId(android.R.id.button1)).perform(click());
    }

    //ввод времени через клавиатуру
    public void timeInputWithKeyBoard(String hour, String minute) {
        int hourPosition = 0;
        int timePosition = 3;

        controlElements.pickButtonOut(NewsPageLocators.NEWS_ITEM_PUBLISH_TIME_TEXT_INPUT_EDIT_TEXT);
        controlElements.pickSoftKeyboard();
        controlElements.inputTimeBySofKeyBoard(hour, hourPosition);
        controlElements.inputTimeBySofKeyBoard(minute, timePosition);
        controlElements.pickScrollButtonByID(AndroidElementLocators.ID_BUTTON1);
    }

    //добавление новости с текущей датой и текущим временем
    public void addTodayNews(int categoryPosition, String newsTitle, String newsDescription) {
        controlElements.pickButtonOut(NewsPageLocators.EDIT_NEWS_MATERIAL_BUTTON);
        controlElements.pickButtonOut(NewsPageLocators.ADD_NEWS_IMAGE_VIEW);
        pickNewsCategory(categoryPosition); //Выбор катеории
        textInput(NewsPageLocators.NEWS_ITEM_TITLE_TEXT_INPUT_EDIT_TEXT, newsTitle); //Заполнение названия новости
        controlElements.pickButtonOut(NewsPageLocators.NEWS_ITEM_PUBLISH_DATE_TEXT_INPUT_EDIT_TEXT);
        controlElements.pickScrollButtonByID(AndroidElementLocators.ID_BUTTON1);
        controlElements.pickButtonOut(NewsPageLocators.NEWS_ITEM_PUBLISH_TIME_TEXT_INPUT_EDIT_TEXT);
        controlElements.pickScrollButtonByID(AndroidElementLocators.ID_BUTTON1);
        textInput(NewsPageLocators.NEWS_ITEM_DESCRIPTION_TEXT_INPUT_EDIT_TEXT, newsDescription); //Заполнение описание новости
        controlElements.pickScrollButtonByID(NewsPageLocators.SAVE_BUTTON);
    }

    //добавление новости со случайной датой и текущим временем
    public void addNewsWithAnyDate(int categoryPosition, String newsTitle, String newsDescription, int day, int month, int year) {
        controlElements.pickButtonOut(NewsPageLocators.EDIT_NEWS_MATERIAL_BUTTON);
        controlElements.pickButtonOut(NewsPageLocators.ADD_NEWS_IMAGE_VIEW);
        pickNewsCategory(categoryPosition);
        textInput(NewsPageLocators.NEWS_ITEM_TITLE_TEXT_INPUT_EDIT_TEXT, newsTitle);
        setDateByDataPicker(NewsPageLocators.NEWS_ITEM_PUBLISH_DATE_TEXT_INPUT_EDIT_TEXT, year, month, day);
        controlElements.pickButtonOut(NewsPageLocators.NEWS_ITEM_PUBLISH_TIME_TEXT_INPUT_EDIT_TEXT);
        controlElements.pickScrollButtonByID(AndroidElementLocators.ID_BUTTON1);
        textInput(NewsPageLocators.NEWS_ITEM_DESCRIPTION_TEXT_INPUT_EDIT_TEXT, newsDescription);
        controlElements.pickScrollButtonByID(NewsPageLocators.SAVE_BUTTON);
    }

    //выбор случайной даты и случайного времени при создании новости
    public void addNewsWithAnyDateAndTime(int categoryPosition, String newsTitle, String newsDescription, int day, int month, int year, String hour, String minute) {
        controlElements.pickButtonOut(NewsPageLocators.EDIT_NEWS_MATERIAL_BUTTON);
        controlElements.pickButtonOut(NewsPageLocators.ADD_NEWS_IMAGE_VIEW);
        pickNewsCategory(categoryPosition);
        textInput(NewsPageLocators.NEWS_ITEM_TITLE_TEXT_INPUT_EDIT_TEXT, newsTitle);
        setDateByDataPicker(NewsPageLocators.NEWS_ITEM_PUBLISH_DATE_TEXT_INPUT_EDIT_TEXT, year, month, day);

        controlElements.pickButtonOut(NewsPageLocators.NEWS_ITEM_PUBLISH_TIME_TEXT_INPUT_EDIT_TEXT);
        timeInputWithKeyBoard(hour, minute);
        textInput(NewsPageLocators.NEWS_ITEM_DESCRIPTION_TEXT_INPUT_EDIT_TEXT, newsDescription);
        controlElements.pickScrollButtonByID(NewsPageLocators.SAVE_BUTTON);
    }

    //ввод случайных величин "час" и "минуты" с клавиатуры
    public void inputRandomHourAndMinuteWithKeyboard(String hour, String minute) {
        controlElements.pickButtonOut(NewsPageLocators.EDIT_NEWS_MATERIAL_BUTTON);
        controlElements.pickButtonOut(NewsPageLocators.ADD_NEWS_IMAGE_VIEW);
        timeInputWithKeyBoard(hour, minute);
    }
}
