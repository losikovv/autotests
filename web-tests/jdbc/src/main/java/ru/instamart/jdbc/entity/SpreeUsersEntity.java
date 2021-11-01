package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class SpreeUsersEntity {

    private Long id;
    private String encryptedPassword;
    private String passwordSalt;
    private String email;
    private String rememberToken;
    private String persistenceToken;
    private String resetPasswordToken;
    private Integer signInCount;
    private Integer failedAttempts;
    private String lastRequestAt;
    private String currentSignInAt;
    private String lastSignInAt;
    private String currentSignInIp;
    private String lastSignInIp;
    private String login;
    private String authenticationToken;
    private String unlockToken;
    private String lockedAt;
    private String resetPasswordSentAt;
    private String createdAt;
    private String updatedAt;
    private String spreeApiKey;
    private String rememberCreatedAt;
    private String firstname;
    private String lastname;
    private String avatarFileName;
    private String avatarUpdatedAt;
    private String socialProfile;
    private Integer b2b;
    private String customerComment;
    private Integer shippedAndPaidOrdersCount;
    private String uuid;
    private String privacyTermsAcceptedAt;
    private String promoTermsChangedAt;
    private Integer promoTermsAccepted;
    private String tenantIdBackup;
    private String tenantId;
    private Integer adult;
    private String pendingEmail;
    private String deletedAt;
    private Integer deletedById;
    private Integer unsubscribedViaCustomerSupport;
    private String sessionToken;
}
