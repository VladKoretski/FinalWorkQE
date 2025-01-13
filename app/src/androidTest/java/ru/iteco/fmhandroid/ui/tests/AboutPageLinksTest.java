package ru.iteco.fmhandroid.ui.tests;

import android.content.Intent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.intent.Intents;
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
import io.qameta.allure.kotlin.Story;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.EspressoIdlingResource;
import ru.iteco.fmhandroid.ui.datasources.Constants;
import ru.iteco.fmhandroid.ui.operations.CheckElements;
import ru.iteco.fmhandroid.ui.operations.ControlElements;
import ru.iteco.fmhandroid.ui.operations.UserAuthorization;
import ru.iteco.fmhandroid.ui.pages.AboutPageLocators;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)

public class AboutPageLinksTest {

    @Rule
    public ActivityTestRule<AppActivity> mActivityScenarioRule =
            new ActivityTestRule<>(AppActivity.class);
    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.END,
            String.valueOf(System.currentTimeMillis()));

    UserAuthorization userAuthorization = new UserAuthorization();
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
        controlElements.pickOutMainMenuLine(Constants.MENU_ABOUT_EN, Constants.MENU_ABOUT_RU);
        Intents.init();
    }

    @After
    public void unregisterIdlingResources() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource);
        Intents.release();
    }

    @Epic(value = "Переходы по ссылкам со страницы About")
    @Feature(value = "Ссылка About privacy policy")
    @Story(value = "Проверка открытия страницы")
    @Test
    public void moveToPrivacyPolicyWebPageTest() {
        ControlElements.performClick(AboutPageLocators.PRIVACY_POLICY_TEXT_VIEW);
        CheckElements.checkIntent(Intent.ACTION_VIEW, Constants.URL_PRIVACY_POLICY);
    }

    @Epic(value = "Переходы по ссылкам со страницы About")
    @Feature(value = "Ссылка About terms of use")
    @Story(value = "Проверка открытия страницы")
    @Test
    public void moveToTermsOfUseWebPageTest() {
        ControlElements.performClick(AboutPageLocators.TERMS_OF_USE_TEXT_VIEW);
        CheckElements.checkIntent(Intent.ACTION_VIEW, Constants.URL_TERMS_OF_USE);
    }
}

