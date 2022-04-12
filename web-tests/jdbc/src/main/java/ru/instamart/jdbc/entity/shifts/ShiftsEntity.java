package ru.instamart.jdbc.entity.shifts;

import lombok.Data;

@Data
public class ShiftsEntity {
    private Long id;
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private String partner_id;
    private String planning_area_id;
    private String role;
    private String pauses_limit_minutes;
    private String planning_periods_ids;
    private String plan_started_at;
    private String plan_ended_at;
    private String fact_started_at;
    private String fact_ended_at;
    private String state;
    private String cancellation_reason;
    private String guaranteed_payroll;
    private String planning_area_name;
    private String max_fix_payroll_per_hour;
    private String surged;
    private String store_uuid;
    private String region_id;
    private String tried_point;
    private String admin_correction_author;
    private String admin_correction_reason;
    private String penalty;
    private String delivery_area_id;
    private String schedule_type;
    private String fixed_on_delivery_area_or_store;
    private String express_delivery;
}
