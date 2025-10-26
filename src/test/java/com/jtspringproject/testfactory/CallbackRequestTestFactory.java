package com.jtspringproject.testfactory;

import com.jtspringproject.carproject.models.CallbackRequest;

import java.time.LocalDateTime;

public class CallbackRequestTestFactory {

    public static CallbackRequest createDefault() {
        CallbackRequest request = new CallbackRequest();
        request.setName("Наталья");
        request.setPhone("+375291234567");
        request.setComment("Прошу перезвонить по поводу автомобиля.");
        request.setConsentPersonal(true);
        request.setSubmittedAt(LocalDateTime.of(2025, 10, 11, 12, 30));
        return request;
    }

    public static CallbackRequest createWithoutConsent() {
        CallbackRequest request = createDefault();
        request.setConsentPersonal(false);
        request.setComment("Оставил заявку, но не дал согласие на обработку.");
        return request;
    }

    public static CallbackRequest createWithLongComment() {
        CallbackRequest request = createDefault();
        request.setComment("Очень интересует электромобиль XPeng P7. Хотел бы узнать о наличии, условиях покупки, возможностях тест-драйва и вариантах финансирования. Также интересует гарантия, сервисное обслуживание и наличие зарядной инфраструктуры в Минске.");
        return request;
    }

    public static CallbackRequest createWithInvalidPhone() {
        CallbackRequest request = createDefault();
        request.setPhone("abc123"); // некорректный формат
        return request;
    }

    public static CallbackRequest createValid() {
        CallbackRequest request = new CallbackRequest();
        request.setName("Наталья Иванова");
        request.setPhone("+375291234567");
        request.setComment("Прошу перезвонить завтра после 12:00");
        request.setConsentPersonal(true);
        request.setSubmittedAt(LocalDateTime.now());
        return request;
    }
}
