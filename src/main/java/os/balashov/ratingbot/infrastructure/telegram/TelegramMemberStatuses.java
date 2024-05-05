package os.balashov.ratingbot.infrastructure.telegram;

import java.util.List;

public enum TelegramMemberStatuses {
    CREATOR,
    ADMINISTRATOR,
    MEMBER,
    LEFT,
    KICKED,
    RESTRICTED,
    UNKNOWN;

    private static final List<TelegramMemberStatuses> adminStatuses = List.of(CREATOR, ADMINISTRATOR);
    private static final List<TelegramMemberStatuses> notMemberStatuses = List.of(LEFT, KICKED, RESTRICTED);

    public static TelegramMemberStatuses fromString(String status) {
        for (TelegramMemberStatuses value : values()) {
            if (value.name().equalsIgnoreCase(status)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }

    public static boolean isMemberStatus(String status) {
        return !notMemberStatuses.contains(fromString(status));
    }

    public static boolean isAdminStatus(String status) {
        return adminStatuses.contains(fromString(status));
    }
}