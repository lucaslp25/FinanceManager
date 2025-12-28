package com.lpdev.financemanagerapi.dashboards.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Getter
@Immutable
@Table(name = "vw_dashboard_category_allocation")
public class DashboardAllocationView {

    @Id
    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "a_month")
    private Integer a_month;

    @Column(name = "a_year")
    private Integer a_year;

    private BigDecimal total;
}