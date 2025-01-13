package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Flaky;
import io.qameta.allure.kotlin.Story;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.EspressoIdlingResource;
import ru.iteco.fmhandroid.ui.datasources.Constants;
import ru.iteco.fmhandroid.ui.operations.CheckElements;
import ru.iteco.fmhandroid.ui.operations.ControlElements;
import ru.iteco.fmhandroid.ui.operations.UserAuthorization;
import ru.iteco.fmhandroid.ui.pages.AuthorizationPageLocators;
import ru.iteco.fmhandroid.ui.pages.MainMenuLocators;
import ru.iteco.fmhandroid.ui.pages.MissionPageLocators;
import ru.iteco.fmhandroid.ui.pages.NewsPageLocators;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class ElementsExistenceTest {

    @Rule
    public ActivityTestRule<AppActivity> mActivityScenarioRule =
            new ActivityTestRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.END,
            String.valueOf(System.currentTimeMillis()));

    UserAuthorization userAuthorization = new UserAuthorization();
    CheckElements checkElements = new CheckElements();
    ControlElements controlElements = new ControlElements();

    @Before
    public void registerIdlingResources() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource);
        try {
            userAuthorization.checkIfNotAuthorized();
        } catch (NoMatchingViewException e) {
            userAuthorization.logOutUser();
        }
        userAuthorization.inputLoginAndPassword(Constants.USER_LOGIN_TEST, Constants.USER_PASSWORD_TEST);
    }

    @After
    public void unregisterIdlingResources() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource);
    }

    @Flaky
    @Epic(value = "Существование элементов на страницах")
    @Feature(value = "Страница авторизации")
    @Story(value = "Проверка наличия строки ввода логина")
    @Test
    public void checkLoginLineExistenceTest() {
        userAuthorization.logOutUser();
        ViewInteraction EnteringLogin = onView(
                anyOf(withHint(Constants.HINT_TEXT_LOGIN_EN),
                        withHint(Constants.HINT_TEXT_LOGIN_RU)));
        EnteringLogin.check(matches(isDisplayed()));
    }

    @Flaky
    @Epic(value = "Существование элементов на страницах")
    @Feature(value = "Страница авторизации")
    @Story(value = "Проверка наличия строки ввода пароля")
    @Test
    public void checkPasswordLineExistenceTest() {
        userAuthorization.logOutUser();
        ViewInteraction EnteringPassword = onView(
                anyOf(withHint(Constants.HINT_TEXT_PASSWORD_EN),
                        withHint(Constants.HINT_TEXT_PASSWORD_RU)));
        EnteringPassword.check(matches(isDisplayed()));
    }

    @Flaky
    @Epic(value = "Существование элементов на страницах")
    @Feature(value = "Страница авторизации")
    @Story(value = "Проверка наличия кнопки ОК")
    @Test
    public void checkLoginButtonExistenceTest() {
        userAuthorization.logOutUser();
        ViewInteraction materialButton = onView(
                allOf(withId(AuthorizationPageLocators.ENTER_BUTTON)));
        materialButton.check(matches(isDisplayed()));
    }

    @Flaky
    @Epic(value = "Существование элементов на страницах")
    @Feature(value = "Главная страница")
    @Story(value = "Наличие на странице главного меню")
    @Test
    public void checkMainMenuExistenceTest() {
        ViewInteraction appCompatImageButton = onView(withId(MainMenuLocators.MAIN_MENU_IMAGE_BUTTON));
        appCompatImageButton.check(matches(isDisplayed()));
    }

    @Flaky
    @Epic(value = "Существование элементов на страницах")
    @Feature(value = "Главная страница")
    @Story(value = "Наличие на странице кнопки перехада в раздел Наша миссия")
    @Test
    public void checkGoToMissionButtonExistenceTest() {
        ViewInteraction appCompatImageButton = onView(withId(MissionPageLocators.OUR_MISSION_IMAGE_BUTTON));
        appCompatImageButton.check(matches(isDisplayed()));
    }

    @Flaky
    @Epic(value = "Существование элементов на страницах")
    @Feature(value = "Главная страница")
    @Story(value = "Наличие на странице кнопки авторизации")
    @Test
    public void checkLogoutButtonExistenceTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(AuthorizationPageLocators.AUTHORIZATION_IMAGE_BUTTON)));
        appCompatImageButton.check(matches(isDisplayed()));
    }

    @Flaky
    @Epic(value = "Существование элементов на страницах")
    @Feature(value = "Страница новости")
    @Story(value = "Наличие на странице кнопки редактирования новостей")
    @Test
    public void checkNewsEditingButtonExistenceTest() {
        controlElements.pickOutMainMenuLine(Constants.MENU_NEWS_EN, Constants.MENU_NEWS_RU);
        controlElements.pickButtonOut(NewsPageLocators.EDIT_NEWS_MATERIAL_BUTTON);
        checkElements.checkElementWithIdAndContentDescription(
                NewsPageLocators.EDIT_NEWS_ITEM_IMAGE_VIEW,
                Constants.EDIT_BUTTON_CONTENT_DESCRIPTION_EN,
                Constants.EDIT_BUTTON_CONTENT_DESCRIPTION_RU);
    }

    @Flaky
    @Epic(value = "Существование элементов на страницах")
    @Feature(value = "Страница новости")
    @Story(value = "Наличие на странице кнопки удаления новостей")
    @Test
    public void checkDeleteButtonExistenceTest() {
        controlElements.pickOutMainMenuLine(Constants.MENU_NEWS_EN, Constants.MENU_NEWS_RU);
        controlElements.pickButtonOut(NewsPageLocators.EDIT_NEWS_MATERIAL_BUTTON);
        ViewInteraction imageView = onView(
                allOf(withId(NewsPageLocators.DELETE_NEWS_ITEM_IMAGE_VIEW),
                        anyOf(withContentDescription(Constants.DELETE_BUTTON_CONTENT_DESCRIPTION_EN),
                                withContentDescription(Constants.DELETE_BUTTON_CONTENT_DESCRIPTION_RU)),
                        withParent(withParent(withId(NewsPageLocators.NEWS_ITEM_MATERIAL_CARD_VIEW))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));
    }

    @Flaky
    @Epic(value = "Существование элементов на страницах")
    @Feature(value = "Страница новости")
    @Story(value = "Наличие на странице фильтрования новостей")
    @Test
    public void checkFilterNewsCategoryButtonExistenceTest() {
        controlElements.pickOutMainMenuLine(Constants.MENU_NEWS_EN, Constants.MENU_NEWS_RU);
        checkElements.checkControlElements(NewsPageLocators.FILTER_NEWS_MATERIAL_BUTTON);
    }

    @Flaky
    @Epic(value = "Существование элементов на страницах")
    @Feature(value = "Страница новости")
    @Story(value = "Наличие на странице сортировки новостей")
    @Test
    public void checkSortNewsCategoryButtonExistenceTest() {
        controlElements.pickOutMainMenuLine(Constants.MENU_NEWS_EN, Constants.MENU_NEWS_RU);
        checkElements.checkElementWithIdAndContentDescription(NewsPageLocators.SORT_NEWS_MATERIAL_BUTTON,
                Constants.SORT_NEWS_BUTTON_EN, Constants.SORT_NEWS_BUTTON_RU);
    }

    @Flaky
    @Epic(value = "Существование элементов на страницах")
    @Feature(value = "Страница новости")
    @Story(value = "Наличие на странице кнопки редактирования новостей")
    @Test
    public void checkEditNewsButtonExistenceTest() {
        controlElements.pickOutMainMenuLine(Constants.MENU_NEWS_EN, Constants.MENU_NEWS_RU);
        checkElements.checkControlElements(NewsPageLocators.EDIT_NEWS_MATERIAL_BUTTON);
    }

    @Flaky
    @Epic(value = "Существование элементов на страницах")
    @Feature(value = "Страница новости")
    @Story(value = "Наличие на странице кнопки обновления страницы")
    @Test
    public void checkRetryNewsButtonExistenceTest() {
        controlElements.pickOutMainMenuLine(Constants.MENU_NEWS_EN, Constants.MENU_NEWS_RU);
        checkElements.checkElementByIdAndText(NewsPageLocators.NEWS_RETRY_MATERIAL_BUTTON,
                Constants.BUTTON_REFRESH_NEWS_EN, Constants.BUTTON_REFRESH_NEWS_RU);
    }

    @Epic(value = "Существование элементов на страницах")
    @Feature(value = "Проверка наличия меню на странице About")
    @Story(value = "Проверка возможности перехода на страницу авторизации со страницы About с помощью меню")
    @Test
    public void checkMenuOnAboutPageTest() {
        controlElements.pickOutMainMenuLine(Constants.MENU_ABOUT_EN, Constants.MENU_ABOUT_RU);
        checkElements.checkTextWithIDAndClass(MainMenuLocators.MAIN_MENU_IMAGE_BUTTON,
                MainMenuLocators.CONTAINER_CUSTOM_APP_BAR_INCLUDE_ON_FRAGMENT_MAIN,
                Constants.MAIN_MENU_RU, Constants.MAIN_MENU_EN, android.widget.LinearLayout.class);
        userAuthorization.logOutUser();
    }
}