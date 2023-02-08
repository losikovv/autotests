package ru.instamart.test.reforged.stf_prod.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_SID;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Оценка заказа")
public final class OrderEvaluationTests {
    //тесты скипнуты, тк для прохождения по полному циклу заказа нужно проводить его через шоппер,
    //что повесит остальные тесты шоппера
    private final ApiHelper helper = new ApiHelper();

    @Skip
    @TmsLink("3220")
    @Test(description = "Оценка заказа | положительная", groups = {STF_PROD_S})
    public void orderPositiveEvaluation() {
        final var userData = UserManager.getQaUser();
        helper.makeAndCompleteOrder(userData, DEFAULT_SID, 2);//бд

        shop().goToPageWithAuth(DEFAULT_SID, userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactOrderEvaluation().checkOrderEvaluationModalDisplayed();
        shop().interactOrderEvaluation().setEvaluationValue(5);
        shop().interactOrderEvaluation().checkSendFeedbackDisplayed();
        shop().interactOrderEvaluation().clickSend();
        shop().interactOrderEvaluation().checkOrderEvaluationModalNotDisplayed();
    }

    @Skip
    @TmsLink("3221")
    @Test(description = "Оценка заказа | отрицательная", groups = {STF_PROD_S})
    public void orderNegativeEvaluation() {
        final var userData = UserManager.getQaUser();
        helper.makeAndCompleteOrder(userData, DEFAULT_SID, 2);//бд

        shop().goToPageWithAuth(DEFAULT_SID, userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactOrderEvaluation().checkOrderEvaluationModalDisplayed();
        shop().interactOrderEvaluation().setEvaluationValue(1);
        shop().interactOrderEvaluation().checkSendFeedbackDisplayed();
        shop().interactOrderEvaluation().clickSend();
        shop().interactOrderEvaluation().checkOrderEvaluationModalNotDisplayed();
    }

    @Skip
    @TmsLink("3223")
    @Test(description = "Выбор тегов для оценки заказа", groups = {STF_PROD_S})
    public void orderNegativeEvaluationTags() {
        final var userData = UserManager.getQaUser();
        helper.makeAndCompleteOrder(userData, DEFAULT_SID, 2);//бд

        shop().goToPageWithAuth(DEFAULT_SID, userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactOrderEvaluation().checkOrderEvaluationModalDisplayed();
        shop().interactOrderEvaluation().setEvaluationValue(1);
        shop().interactOrderEvaluation().checkSendFeedbackDisplayed();

        shop().interactOrderEvaluation().clickNegativeTag("Курьер опоздал");
        shop().interactOrderEvaluation().clickNegativeTag("Не приняли карту");
        shop().interactOrderEvaluation().clickSend();
        shop().interactOrderEvaluation().checkOrderEvaluationModalNotDisplayed();
    }

    @Skip
    @TmsLink("3224")
    @Test(description = "Оценка заказа с комментарием", groups = {STF_PROD_S})
    public void orderEvaluationComment() {
        final var userData = UserManager.getQaUser();
        helper.makeAndCompleteOrder(userData, DEFAULT_SID, 2);//бд

        shop().goToPageWithAuth(DEFAULT_SID, userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactOrderEvaluation().checkOrderEvaluationModalDisplayed();
        shop().interactOrderEvaluation().setEvaluationValue(1);
        shop().interactOrderEvaluation().checkSendFeedbackDisplayed();
        shop().interactOrderEvaluation().addComment("Комментарий к заказу");
        shop().interactOrderEvaluation().checkCommentText("Комментарий к заказу");
        shop().interactOrderEvaluation().clickSend();
        shop().interactOrderEvaluation().checkOrderEvaluationModalNotDisplayed();
    }

    @Skip
    @TmsLink("3225")
    @Test(description = "Оценка заказа с фото", groups = {STF_PROD_S})
    public void orderEvaluationPhoto() {
        final var userData = UserManager.getQaUser();
        helper.makeAndCompleteOrder(userData, DEFAULT_SID, 2);//бд

        shop().goToPageWithAuth(DEFAULT_SID, userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactOrderEvaluation().checkOrderEvaluationModalDisplayed();
        shop().interactOrderEvaluation().setEvaluationValue(1);
        shop().interactOrderEvaluation().checkSendFeedbackDisplayed();
        shop().interactOrderEvaluation().attachPhoto("./src/test/resources/data/attachForOrderEvaluation.png");
        shop().interactOrderEvaluation().checkPhotoAdded();
        shop().interactOrderEvaluation().clickSend();
        shop().interactOrderEvaluation().checkOrderEvaluationModalNotDisplayed();
    }
}


