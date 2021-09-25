package com.dvoraninovich.establishment.controller.command.validator;

import com.dvoraninovich.establishment.model.entity.Role;
import com.dvoraninovich.establishment.model.entity.UserStatus;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class UserValidator {
    private static final Logger logger = LogManager.getLogger(UserValidator.class);
    private UserService service = UserServiceImpl.getInstance();
    private static UserValidator instance;

    private static final String LOGIN_REGEXP = "^[A-Za-z_]{3,25}$";
    private static final String PASSWORD_REGEXP = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$";
    private static final String MAIL_REGEXP = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private static final String PHONE_NUM_REGEXP = "^\\+\\d{12}$";
    private static final String CARD_NUM_REGEXP = "^\\d{16}$";
    private static final String CODE_REGEXP = "^[a-zA-Z0-9]{16}$";

    private static final String FILTER_LOGIN_REGEXP = "^[A-Za-z_]{1,25}$";
    private static final String FILTER_MAIL_REGEXP = "^[a-z0-9_.@-]+$";
    private static final String FILTER_PHONE_NUM_REGEXP = "^[+]{0,1}[\\d]{1,12}$";
    private static final String FILTER_CARD_NUM_REGEXP = "^\\d{1,16}$";

    private UserValidator() {
    }

    public static UserValidator getInstance() {
        if (instance == null) {
            instance = new UserValidator();
        }
        return instance;
    }

    public boolean validateLogin(String login){
        boolean result;
        result = Pattern.matches(LOGIN_REGEXP, login);
        return result;
    }

    public boolean validatePassword(String password){
        boolean result;
        result = Pattern.matches(PASSWORD_REGEXP, password);
        return result;
    }

    public boolean validateMail(String mail){
        boolean result;
        result =Pattern.matches(MAIL_REGEXP, mail);
        return result;
    }

    public boolean validatePhoneNum(String phoneNum){
        boolean result;
        result = Pattern.matches(PHONE_NUM_REGEXP, phoneNum);
        return result;
    }

    public boolean validateCardNum(String cardNum){
        boolean result;
        result = Pattern.matches(CARD_NUM_REGEXP, cardNum);
        return result;
    }

    public boolean validateCode(String code){
        boolean result;
        result =Pattern.matches(CODE_REGEXP, code);
        return result;
    }

    public HashMap<String, Boolean> validateUserData(String login, String password, String mail,
                                    String phoneNum, String cardNum){

        HashMap<String, Boolean> validationMessages = new HashMap<>();
        boolean currentResult;

        currentResult = Pattern.matches(LOGIN_REGEXP, login);
        validationMessages.put(INVALID_LOGIN, !currentResult);

        currentResult = Pattern.matches(PASSWORD_REGEXP, password);
        validationMessages.put(INVALID_PASSWORD, !currentResult);

        currentResult = Pattern.matches(MAIL_REGEXP, mail);
        validationMessages.put(INVALID_MAIL, !currentResult);

        currentResult = Pattern.matches(PHONE_NUM_REGEXP, phoneNum);
        validationMessages.put(INVALID_PHONE_NUM, !currentResult);

        currentResult = Pattern.matches(CARD_NUM_REGEXP, cardNum);
        validationMessages.put(INVALID_CARD_NUM, !currentResult);

        currentResult = service.isLoginUnique(login);
        validationMessages.put(NOT_UNIQUE_LOGIN, !currentResult);

        currentResult = service.isMailUnique(mail);
        validationMessages.put(NOT_UNIQUE_MAIL, !currentResult);

        return validationMessages;
    }

    public HashMap<String, Boolean> validateFilterParameters(String login, String mail, String phoneNumber, String cardNumber,
                                                             String[] userStatusesLines, String[] userRolesLine){

        HashMap<String, Boolean> validationMessages = new HashMap<>();
        boolean currentResult;

        if (!login.equals("")) {
            currentResult = Pattern.matches(FILTER_LOGIN_REGEXP, login);
            validationMessages.put(INVALID_LOGIN, !currentResult);
        }

        if (!mail.equals("")) {
            currentResult = Pattern.matches(FILTER_MAIL_REGEXP, mail);
            validationMessages.put(INVALID_MAIL, !currentResult);
        }

        if (!phoneNumber.equals("")) {
            currentResult = Pattern.matches(FILTER_PHONE_NUM_REGEXP, phoneNumber);
            validationMessages.put(INVALID_PHONE_NUM, !currentResult);
        }

        if (!cardNumber.equals("")) {
            currentResult = Pattern.matches(FILTER_CARD_NUM_REGEXP, cardNumber);
            validationMessages.put(INVALID_CARD_NUM, !currentResult);
        }


        currentResult = false;
        ArrayList<String> userStatusesValues = new ArrayList<>();
        for (UserStatus state: UserStatus.values()){
            userStatusesValues.add(state.name());
        }
        for (String line : userStatusesLines) {
            if (!userStatusesValues.contains(line)) {
                currentResult = true;
            }
        }
        validationMessages.put(INVALID_USER_STATUS, currentResult);

        currentResult = false;
        ArrayList<String> userRolesValues = new ArrayList<>();
        for (Role type: Role.values()){
            userRolesValues.add(type.name());
        }
        for (String line : userRolesLine) {
            if (!userRolesValues.contains(line)) {
                currentResult = true;
            }
        }
        validationMessages.put(INVALID_USER_ROLE, currentResult);

        return validationMessages;
    }

}
