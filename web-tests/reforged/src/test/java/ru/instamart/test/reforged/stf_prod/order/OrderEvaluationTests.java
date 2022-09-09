package ru.instamart.test.reforged.stf_prod.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_SID;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Оценка заказа")
public final class OrderEvaluationTests {

    private final ApiHelper helper = new ApiHelper();

    @Skip
    @CaseId(3220)
    @Test(description = "Оценка заказа | положительная", groups = {STF_PROD_S})
    public void orderPositiveEvaluation() {
        UserData userData = UserManager.getQaUser();
        helper.makeAndCompleteOrder(userData, DEFAULT_SID, 2);//бд

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactOrderEvaluation().checkOrderEvaluationModalDisplayed();
        shop().interactOrderEvaluation().setEvaluationValue(5);
        shop().interactOrderEvaluation().checkSendFeedbackDisplayed();
        shop().interactOrderEvaluation().clickSend();
        shop().interactOrderEvaluation().checkOrderEvaluationModalNotDisplayed();
    }

    @Skip
    @CaseId(3221)
    @Test(description = "Оценка заказа | отрицательная", groups = {STF_PROD_S})
    public void orderNegativeEvaluation() {
        UserData userData = UserManager.getQaUser();
        helper.makeAndCompleteOrder(userData, DEFAULT_SID, 2);//бд

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactOrderEvaluation().checkOrderEvaluationModalDisplayed();
        shop().interactOrderEvaluation().setEvaluationValue(1);
        shop().interactOrderEvaluation().checkSendFeedbackDisplayed();
        shop().interactOrderEvaluation().clickSend();
        shop().interactOrderEvaluation().checkOrderEvaluationModalNotDisplayed();
    }

    @Skip
    @CaseId(3223)
    @Test(description = "Выбор тегов для оценки заказа", groups = {STF_PROD_S})
    public void orderNegativeEvaluationTags() {
        UserData userData = UserManager.getQaUser();
        helper.makeAndCompleteOrder(userData, DEFAULT_SID, 2);//бд

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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
    @CaseId(3224)
    @Test(description = "Оценка заказа с комментарием", groups = {STF_PROD_S})
    public void orderEvaluationComment() {
        UserData userData = UserManager.getQaUser();
        helper.makeAndCompleteOrder(userData, DEFAULT_SID, 2);//бд

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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
    @CaseId(3225)
    @Test(description = "Оценка заказа с фото", groups = {STF_PROD_S})
    public void orderEvaluationPhoto() {
        UserData userData = UserManager.getQaUser();
        helper.makeAndCompleteOrder(userData, DEFAULT_SID, 2);//бд

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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


