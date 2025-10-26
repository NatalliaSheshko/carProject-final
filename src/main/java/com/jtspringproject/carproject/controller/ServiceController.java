package com.jtspringproject.carproject.controller;

import com.jtspringproject.carproject.models.ServiceAppointment;
import com.jtspringproject.carproject.repository.ServiceRepository;
import com.jtspringproject.carproject.services.ServiceBlock;
import com.jtspringproject.carproject.validation.OnCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping("")
    public String showServicePage(Model model) {
        Map<String, String> services = Map.of(
                "diagnostics", "КОМПЬЮТЕРНАЯ ДИАГНОСТИКА",
                "body-repair", "КУЗОВНОЙ РЕМОНТ",
                "maintenance", "ТЕХНИЧЕСКОЕ ОБСЛУЖИВАНИЕ",
                "tire-service", "ШИНОМОНТАЖ",
                "electric-repair", "РЕМОНТ АВТОЭЛЕКТРИКИ",
                "equipment-install", "УСТАНОВКА ДОП. ОБОРУДОВАНИЯ"
        );
        model.addAttribute("services", services);
        model.addAttribute("appointment", new ServiceAppointment());
        return "servicePage";
    }

    @GetMapping("/{type}")
    public String showServiceDetail(@PathVariable String type, Model model) {
        String title = switch (type) {
            case "diagnostics" -> "КОМПЬЮТЕРНАЯ ДИАГНОСТИКА";
            case "body-repair" -> "КУЗОВНОЙ РЕМОНТ";
            case "maintenance" -> "ТЕХНИЧЕСКОЕ ОБСЛУЖИВАНИЕ";
            case "tire-service" -> "ШИНОМОНТАЖ";
            case "electric-repair" -> "РЕМОНТ АВТОЭЛЕКТРИКИ";
            case "equipment-install" -> "УСТАНОВКА ДОП. ОБОРУДОВАНИЯ";
            default -> "Услуга";
        };
        model.addAttribute("title", title);
        model.addAttribute("appointment", new ServiceAppointment());
        System.out.println("appointment loaded");
        return "serviceDetail"; // JSP: /WEB-INF/views/serviceDetail.jsp
    }

    @PostMapping("/submit")
    @ResponseBody
    public ResponseEntity<?> submitAppointment(@Validated(OnCreate.class) @ModelAttribute ServiceAppointment appointment,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Ошибка валидации");
        }

        serviceRepository.save(appointment);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/body-repair")
    public String showBodyRepairPage(Model model) {
        List<ServiceBlock> blocks = List.of(
                new ServiceBlock("Устранение вмятин", "Без покраски, быстро, недорого.", "п/п электрок/диск от 234 Вг", "/images/dent.jpg"),
                new ServiceBlock("Покраска автомобиля", "Целиком или частично. Качественно, быстро.", "п/п электрок/диск от 144 Вг", "/images/paint.jpg"),
                new ServiceBlock("Замена стекол", "Установка новых стекол взамен повреждённых.", "п/п электрок/диск от 234 Вг", "/images/glass.jpg"),
                new ServiceBlock("Восстановление геометрии кузова", "После ДТП. Быстро, качественно.", "п/п электрок/диск от 144 Вг", "/images/geometry.jpg"),
                new ServiceBlock("Полировка кузова автомобиля", "Удаление царапин, восстановление блеска.", "п/п электрок/диск от 234 Вг", "/images/polish.jpg"),
                new ServiceBlock("Ремонт пластика, бампера и покраска", "Ремонт и покраска пластиковых деталей.", "п/п электрок/диск от 244 Вг", "/images/plastic.jpg")
        );
        model.addAttribute("title", "Кузовной ремонт");
        model.addAttribute("appointment", new ServiceAppointment());
        model.addAttribute("blocks", blocks);
        return "serviceDetail";
    }

    @GetMapping("/equipment-install")
    public String showEquipmentInstallPage(Model model) {
        List<ServiceBlock> blocks = List.of(
                new ServiceBlock("Установка парктроников", "Парктроники с точной индикацией расстояния и звуковым сигналом.", "от 234 Вг", "/images/parking-sensors.jpeg"),
                new ServiceBlock("Установка камер заднего вида", "Камеры с широким углом обзора и ночным режимом.", "от 144 Вг", "/images/rear-camera.jpeg"),
                new ServiceBlock("Установка доводчиков дверей", "Автоматическое закрытие дверей без усилий.", "от 214 Вг", "/images/door-closer.jpg"),
                new ServiceBlock("Установка электрических подножек", "Выдвижные подножки для удобства посадки и высадки.", "от 244 Вг", "/images/electric-step.jpg"),
                new ServiceBlock("Установка защитных сеток", "Сетки на радиатор и воздухозаборники для защиты от мусора.", "от 23 Вг", "/images/protective-mesh.jpg"),
                new ServiceBlock("Установка шумоизоляции", "Уменьшение шума в салоне, улучшение акустического комфорта.", "от 244 Вг", "/images/soundproofing.jpg")
        );
        model.addAttribute("title", "УСТАНОВКА ДОПОЛНИТЕЛЬНОГО ОБОРУДОВАНИЯ");
        model.addAttribute("blocks", blocks);
        model.addAttribute("appointment", new ServiceAppointment());
        return "serviceDetail";
    }

    @GetMapping("/diagnostics")
    public String showDiagnosticsPage(Model model) {
        List<ServiceBlock> blocks = List.of(
                new ServiceBlock("Опрос электронных блоков авто", "Получение информации от всех доступных электронных систем автомобиля.", "от 144 Вг", "/images/ecu-scan.webp"),
                new ServiceBlock("Считывание и расшифровка кодов неисправности", "Выявление ошибок и их расшифровка для точной диагностики.", "от 44 Вг", "/images/fault-codes.jpg"),
                new ServiceBlock("Адаптация и программирование блоков управления", "Настройка новых компонентов и обновление прошивок.", "от 235 Вг", "/images/module-programming.webp"),
                new ServiceBlock("Диагностика высоковольтной батареи", "Проверка состояния HV-батареи в гибридных и электромобилях.", "от 234 Вг", "/images/hv-battery.jpg"),
                new ServiceBlock("Диагностика рулевого управления", "Проверка электроусилителя, датчиков угла и механики.", "от 244 Вг", "/images/steering-diagnostics.webp"),
                new ServiceBlock("Диагностика тормозной системы", "Анализ работы ABS, ESP и состояния тормозных механизмов.", "от 234 Вг", "/images/brake-diagnostics.jpg")
        );
        model.addAttribute("title", "КОМПЬЮТЕРНАЯ ДИАГНОСТИКА");
        model.addAttribute("blocks", blocks);
        model.addAttribute("appointment", new ServiceAppointment());
        return "serviceDetail";
    }

    @GetMapping("/maintenance")
    public String showMaintenancePage(Model model) {
        List<ServiceBlock> blocks = List.of(
                new ServiceBlock("Регламентированное ТО", "Плановое техническое обслуживание согласно рекомендациям производителя.", "от 144 Вг", "/images/scheduled-maintenance.png"),
                new ServiceBlock("Ремонт подвески", "Замена амортизаторов, пружин, сайлентблоков и других элементов подвески.", "от 234 Вг", "/images/suspension-repair.webp"),
                new ServiceBlock("Ремонт рулевого управления", "Проверка и восстановление рулевой рейки, насоса ГУР, электроусилителя.", "от 244 Вг", "/images/steering-repair.jpg"),
                new ServiceBlock("Ремонт тормозной системы", "Замена колодок, дисков, суппортов, диагностика ABS и ESP.", "от 144 Вг", "/images/brake-repair.jpg"),
                new ServiceBlock("Ремонт трансмиссии", "Обслуживание и ремонт МКПП, АКПП, вариаторов и раздаточных коробок.", "от 24 Вг", "/images/transmission-repair.jpg"),
                new ServiceBlock("Ремонт ДВС", "Диагностика, замена прокладок, цепей, поршневой группы, капитальный ремонт.", "от 244 Вг", "/images/engine-repair.jpg")
        );
        model.addAttribute("title", "ТЕХНИЧЕСКОЕ ОБСЛУЖИВАНИЕ");
        model.addAttribute("blocks", blocks);
        model.addAttribute("appointment", new ServiceAppointment());
        return "serviceDetail";
    }

    @GetMapping("/tire-service")
    public String showTireServicePage(Model model) {
        List<ServiceBlock> blocks = List.of(
                new ServiceBlock("Снятие и установка колес", "Быстрая и безопасная замена колес с соблюдением всех норм.", "от 144 Вг", "/images/wheel-mount.jpg"),
                new ServiceBlock("Замена шин", "Профессиональная замена летней, зимней или всесезонной резины.", "от 234 Вг", "/images/tire-change.jpg"),
                new ServiceBlock("Балансировка колес", "Точная балансировка для комфортного и безопасного движения.", "от 234 Вг", "/images/wheel-balance.jpg"),
                new ServiceBlock("Ремонт проколов", "Локальный ремонт проколов с гарантией герметичности.", "от 244 Вг", "/images/puncture-repair.webp"),
                new ServiceBlock("Монтаж/демонтаж шин", "Снятие и установка шин с использованием современного оборудования.", "от 144 Вг", "/images/tire-mount.jpg"),
                new ServiceBlock("Подкачка колес и проверка давления", "Контроль давления и подкачка до рекомендованных значений.", "от 44 Вг", "/images/tire-pressure.jpg")
        );
        model.addAttribute("title", "ШИНОМОНТАЖ");
        model.addAttribute("blocks", blocks);
        model.addAttribute("appointment", new ServiceAppointment());
        return "serviceDetail";
    }

    @GetMapping("/electric-repair")
    public String showElectricRepairPage(Model model) {
        List<ServiceBlock> blocks = List.of(
                new ServiceBlock("Компьютерная диагностика электронных блоков управления", "Сканирование и анализ состояния всех электронных систем автомобиля.", "от 234 Вг", "/images/ecu-diagnostics.jpg"),
                new ServiceBlock("Ремонт повреждённой проводки", "Поиск и восстановление повреждённых участков электропроводки.", "от 234 Вг", "/images/wiring-repair.jpg"),
                new ServiceBlock("Замена предохранителей и реле", "Диагностика и замена перегоревших элементов защиты цепей.", "от 144 Вг", "/images/fuse-replacement.jpg"),
                new ServiceBlock("Проверка и замена аккумулятора", "Тестирование ёмкости, напряжения и установка нового АКБ.", "от 244 Вг", "/images/battery-check.webp"),
                new ServiceBlock("Замена датчиков", "Диагностика и установка новых датчиков ABS, температуры, давления и др.", "от 244 Вг", "/images/sensor-replacement.jpg"),
                new ServiceBlock("Проверка массы и напряжения в цепях", "Контроль целостности цепей, уровня напряжения и качества массы.", "от 24 Вг", "/images/voltage-check.png")
        );
        model.addAttribute("title", "РЕМОНТ АВТОЭЛЕКТРИКИ");
        model.addAttribute("blocks", blocks);
        model.addAttribute("appointment", new ServiceAppointment());
        return "serviceDetail";
    }

}