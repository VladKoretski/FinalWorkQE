package ru.iteco.fmhandroid.ui.operations;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.junit.Assert.fail;

import androidx.test.espresso.ViewInteraction;

import org.hamcrest.Matchers;

import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.ui.pages.NewsPageLocators;

public class CheckElements {

    UserAuthorization userAuthorization = new UserAuthorization();

    @Step("Проверка выполнения Intent по действию {action} и переданным данным {data}")
    public static void checkIntent(String action, String data) {
        intended(allOf(
                hasAction(action),
                hasData(data)));
    }

    private void handleException(Exception e, String message) {
        fail(message + ": " + e.getMessage());
    }

    @Step("Проверка появления управляющего элемента по ID")
    public void checkControlElements(int elementID) {
        try {
            ViewInteraction appCompatImageButton = onView(withId(elementID));
            appCompatImageButton.check(matches(isDisplayed()));
        } catch (Exception e) {
            handleException(e, "Элемент с id " + elementID + " не найден на странице.");
        }
    }

    @Step("Проверка появление текста {expectedText}")
    public void checkTextElements(int elementID, int parentElementID, String expectedText) {
        try {
            ViewInteraction textView = onView(
                    allOf(withId(elementID), withText(expectedText),
                            withParent(withParent(withId(parentElementID))),
                            isDisplayed()));
            textView.check(matches(withText(expectedText)));
        } catch (Exception e) {
            handleException(e, "Текст '" + expectedText + "' не найден на странице.");
        }
    }

    @Step("Проверка появление текста {expectedTextEn} или {expectedTextRu}")
    public void checkText(String expectedTextEn, String expectedTextRu) {
        try {
            ViewInteraction textView = onView(
                    anyOf(withText(expectedTextEn), withText(expectedTextRu)));
            textView.check(matches(isDisplayed()));
        } catch (Exception e) {
            handleException(e, "Ни один из текстов '" + expectedTextEn + "', '" + expectedTextRu + "' не найден на странице.");
        }
    }

    @Step("Проверка появление текста {expectedTextEn} или {expectedTextRu}")
    public void checkTextWithIDAndClass(int elementID, int parentID, String expectedTextEn, String expectedTextRu, Class<?> classElement) {
        try {
            ViewInteraction textView = onView(
                    allOf(withId(elementID), anyOf(withText(expectedTextEn), withText(expectedTextRu)),
                            withParent(allOf(withId(parentID),
                                    withParent(Matchers.instanceOf(classElement))))));
            textView.check(matches(isDisplayed()));
        } catch (Exception e) {
            handleException(e, "Текст '" + expectedTextEn + "' или '" + expectedTextRu + "' не найден на странице.");
        }
    }

    @Step("Проверка появление всплывающего сообщения {expectedTextEn} или {expectedTextRu}")
    public void checkElementWithContentDescription(String expectedTextEn, String expectedTextRu) {
        try {
            ViewInteraction imageView = onView(
                    allOf(anyOf(withContentDescription(expectedTextEn), withContentDescription(expectedTextRu)),
                            withParent(withParent(Matchers.instanceOf(android.widget.LinearLayout.class))),
                            isDisplayed()));
            imageView.check(matches(isDisplayed()));
        } catch (Exception e) {
            handleException(e, "Сообщение '" + expectedTextEn + "' или '" + expectedTextRu + "' не найдено на странице.");
        }
    }

    @Step("Проверка появление всплывающего сообщения {expectedTextEn} или {expectedTextRu}")
    public void checkInstanceElementWithText(String expectedTextEn, String expectedTextRu) {
        try {
            ViewInteraction textView = onView(
                    allOf(Matchers.instanceOf(android.widget.TextView.class), anyOf(withText(expectedTextEn), withText(expectedTextRu)),
                            withParent(allOf(Matchers.instanceOf(android.widget.RelativeLayout.class),
                                    withParent(Matchers.instanceOf(android.widget.RelativeLayout.class)))),
                            isDisplayed()));
            textView.check(matches(isDisplayed()));
        } catch (Exception e) {
            handleException(e, "Текст '" + expectedTextEn + "' или '" + expectedTextRu + "' не найден на странице.");
        }
    }

    @Step("Проверка появления управляющего элемента {contentDescriptionEn} или {contentDescriptionRu}")
    public void checkElementWithIdAndContentDescription(int Id, String contentDescriptionEn, String contentDescriptionRu) {
        try {
            ViewInteraction imageView = onView(
                    allOf(withId(Id), anyOf(withContentDescription(contentDescriptionEn),
                            withContentDescription(contentDescriptionRu))));
            imageView.check(matches(isDisplayed()));
        } catch (Exception e) {
            handleException(e, "Элемент с id " + Id + " и содержанием '" + contentDescriptionEn + "' или '" + contentDescriptionRu + "' не найден на странице.");
        }
    }

    @Step("Проверка появления управляющего элемента по ID и тексту {textEn} или {textRu}")
    public void checkElementByIdAndText(int id, String textEn, String textRu) {
        try {
            ViewInteraction button = onView(
                    allOf(withId(id), anyOf(withText(textEn), withText(textRu)),
                            withParent(allOf(withId(NewsPageLocators.ALL_NEWS_CARDS_BLOCK_CONSTRAINT_LAYOUT),
                                    withParent(withId(NewsPageLocators.CONTAINER_LIST_NEWS_INCLUDE)))),
                            isDisplayed()));
            button.check(matches(isDisplayed()));
        } catch (Exception e) {
            handleException(e, "Элемент с id " + id + " и текстом '" + textEn + "' или '" + textRu + "' не найден на странице.");
        }
    }

    @Step("Проверка введения логина {login} и пароля {password}")
    public void verifyLoginAndCheckElement(String login, String password,
                                           String contentDescriptionEn, String contentDescriptionRu) {
        userAuthorization.inputLoginAndPassword(login, password);
        checkElementWithContentDescription(contentDescriptionEn, contentDescriptionRu);
    }

    @Step("Проверка введения логина {login} и пароля {password}")
    public void verifyLoginAndCheckText(String login, String password,
                                        String expectedTextEn, String expectedTextRu) {
        userAuthorization.inputLoginAndPassword(login, password);
        checkText(expectedTextEn, expectedTextRu);
    }
}

