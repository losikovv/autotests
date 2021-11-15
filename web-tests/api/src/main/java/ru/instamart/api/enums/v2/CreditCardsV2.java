package ru.instamart.api.enums.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum CreditCardsV2 {
    CARD1("VISA", "4111 1111 1111 1111", "11","24", "123",
            "U6o9eEj/Uh5i2UaAEk01u6gvv3sy6VnOIhJUNsARY2p9JoX47KnpqEwxdIiv+N7LVvyI4niuNigwvSxxgsuKtcZndjUX/QWuBRQVWYelWXOl0p9E4fd0dRPmTXZ7y5FB97/vfvDaNa/khnVKMk3bOnFWKjC/uGXnl3urEuC/2iACT0u2x932XCs0jRFwpiES8fwoFhleE0ATtShIwjXrfMsvmCQiioZ1C0Qsx7zYLmcn6g+iNR9/0T8mcnOnHaSkTSW/ElsB5MiqFhQonloUExvQ6ZqQeutDqaQWQw+hpeL6bXt+Ew6qU7mHpcwdyqYik3ev/yeIYLrVqG1HtqVuyw=="),
    CARD2("MasterCard", "5555 5555 5555 5599", "12","24", "123",
            "XkwXeIEFp0XAgzitCFtYsUVJy2jw5wHiSW5+MjzYAOt38lAF/5w2W1RXb83pmasy4R4L8/uenF6hgWxzbe81them0GLTuTMCKJTuOq9U8XAQGNseN0jM4z5xPag1N3gWvZmFHXqUdNwbjzxNwwoWev6EQBwPCFZb8HoUCcDTS4WTqLbbUS85xbNPqCeV8RwEPyLS/Y3lEDcQIH5U5/g0GAv9ZvlXohPyz41WNPSqTd30wIIyPH7Ok/zV9l/2Fyvvl5JnNIRq8mNbjTqgcmOcG06yHzx9kCwHAirf3sFV3NYuwrX4zmL1e219Bx8LCnrl1mBeMJ88X/3akv0jLEoWYQ==");


    private final String type;
    private final String number;
    private final String month;
    private final String year;
    private final String csv;
    private final String cryptogramPacket;
}
