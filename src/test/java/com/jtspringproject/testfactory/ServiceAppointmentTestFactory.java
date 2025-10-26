package com.jtspringproject.testfactory;

import com.jtspringproject.carproject.models.ServiceAppointment;

import java.time.LocalDateTime;

public class ServiceAppointmentTestFactory {

    public static ServiceAppointment createDefault() {
        ServiceAppointment appointment = new ServiceAppointment();
        appointment.setFullName("Наталья Иванова");
        appointment.setPhone("+375291234567");
        appointment.setConsent(true);
        appointment.setSubmittedAt(LocalDateTime.of(2025, 10, 11, 13, 30));
        appointment.setProcessed(false);
        return appointment;
    }

    public static ServiceAppointment createProcessed() {
        ServiceAppointment appointment = createDefault();
        appointment.setProcessed(true);
        return appointment;
    }

    public static ServiceAppointment createWithoutConsent() {
        ServiceAppointment appointment = createDefault();
        appointment.setConsent(false);
        appointment.setPhone("+375291111111");
        return appointment;
    }

    public static ServiceAppointment createWithCustomTime(LocalDateTime time) {
        ServiceAppointment appointment = createDefault();
        appointment.setSubmittedAt(time);
        return appointment;
    }

    public static ServiceAppointment createWithCustomName(String name) {
        ServiceAppointment appointment = createDefault();
        appointment.setFullName(name);
        return appointment;
    }

    public static ServiceAppointment createValid() {
        ServiceAppointment appointment = new ServiceAppointment();
        appointment.setFullName("Наталья Иванова");
        appointment.setPhone("+375291234567");
        appointment.setConsent(true);
        appointment.setSubmittedAt(LocalDateTime.now());
        appointment.setProcessed(false);
        return appointment;
    }

    public static ServiceAppointment createWithProcessedAndTime(boolean processed, LocalDateTime time) {
        ServiceAppointment appointment = createDefault();
        appointment.setProcessed(processed);
        appointment.setSubmittedAt(time);
        return appointment;
    }

    public static ServiceAppointment createWithProcessed(boolean processed) {
        ServiceAppointment appointment = new ServiceAppointment();
        appointment.setFullName("Наталья");
        appointment.setPhone("+375291234567");
        appointment.setConsent(true);
        appointment.setSubmittedAt(LocalDateTime.now());
        appointment.setProcessed(processed);
        appointment.setServiceType("Диагностика");
        return appointment;
    }
}
