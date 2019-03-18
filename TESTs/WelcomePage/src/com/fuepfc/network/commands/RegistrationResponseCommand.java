package com.fuepfc.network.commands;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 18/03/19
 * Time: 11.09
 */
public class RegistrationResponseCommand extends Command{
    private final Registration registration;

    public RegistrationResponseCommand(Registration registration){
        this.registration = registration;
    }

    public Registration getRegistration() {
        return registration;
    }

    public enum Registration {
        ERROR,
        SUCCESSFUL,
        DUPLICATED_ENTRY,
        EMPTY_FIELDS,
        STRING_TOO_LONG
    }
}