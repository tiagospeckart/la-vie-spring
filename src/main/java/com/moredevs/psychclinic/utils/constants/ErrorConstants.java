package com.moredevs.psychclinic.utils.constants;

public class ErrorConstants {

    public static class Admin {
        public static final String ADMIN_INSERTION_ERROR = "Error on inserting Admin";
        public static final String ADMIN_EMPTY_ID = "Admin ID must be provided for update";
        public static final String ADMIN_ID_NOT_FOUND = "Admin with the specified ID does not exist";
    }

    public static class Psychologist {
        public static final String PSYCHOLOGIST_INSERTION_ERROR = "Error on inserting Psychologist";
        public static final String PSYCHOLOGIST_NULL_ID = "Null Psychologist ID";
        public static final String PSYCHOLOGIST_UPDATE_ERROR = "Error on updating Psychologist";
        public static final String PSYCHOLOGIST_ID_NOT_FOUND = "Psychologist Id was not found";
    }

    public static class Client {
        public static final String CLIENT_INSERTION_ERROR = "Error on inserting Client";
        public static final String CLIENT_NULL_ID = "Null Client ID";
        public static final String CLIENT_UPDATE_ERROR = "Error on updating Client";
        public static final String CLIENT_ID_NOT_FOUND = "Client Id was not found";
    }

    public static class Session {
        public static final String SESSION_INSERTION_ERROR = "Error on inserting Session";
        public static final String SESSION_ID_NULL = "Null Session ID";
        public static final String SESSION_ID_NOT_FOUND = "Session Id was not found";
        public static final String SESSION_UPDATE_ERROR = "Error on updating Session";
        public static final String SESSION_COMPLETION_ERROR = "Session couldn't be completed";
        public static final String SESSION_CANCELLATION_ERROR = "Session could not be cancelled";
        public static final String SESSION_RESCHEDULE_ERROR = "An error occurred while rescheduling the session: ";
        public static final String SESSION_RESCHEDULE_FAILURE = "Failed to reschedule the session";
        public static final String SESSION_RESCHEDULE_INVALID_STATE = "Invalid session state for rescheduling: ";
        public static final String SESSION_RESCHEDULE_ONLY_PLANNED = "Invalid session state for rescheduling: ";
    }
}
