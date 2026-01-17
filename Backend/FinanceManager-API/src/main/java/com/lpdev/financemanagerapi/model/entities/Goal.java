package com.lpdev.financemanagerapi.model.entities;

import com.lpdev.financemanagerapi.model.enums.GoalPriority;
import com.lpdev.financemanagerapi.model.enums.GoalStatus;
import com.lpdev.financemanagerapi.security.model.entities.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_goal")
public class Goal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 50)
    private String name;

    private String description;
    private BigDecimal targetAmount;
    private Instant deadline;
    private Instant initDate;

    private BigDecimal currentAmount = BigDecimal.ZERO;

    // init with total value.
    private BigDecimal remainingPay = targetAmount;

    @Enumerated(EnumType.STRING)
    private GoalStatus goalStatus;

    @Enumerated(EnumType.STRING)
    private GoalPriority goalPriority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateCurrentAmount(BigDecimal amount){
        if (this.currentAmount == null) {
            this.currentAmount = BigDecimal.ZERO; //validation - avoid NPE
        }
        this.currentAmount = this.currentAmount.add(amount);
    }

    public void updateRemainingPay(BigDecimal amount){
        if (this.remainingPay == null) {
            this.remainingPay = BigDecimal.ZERO;
        }
        this.remainingPay = this.remainingPay.subtract(amount);
    }

    public void recalculateRemaining() {
        if (this.targetAmount == null) return;

        BigDecimal current = this.currentAmount != null ? this.currentAmount : BigDecimal.ZERO;
        this.remainingPay = this.targetAmount.subtract(current);
    }

    public double getCurrentPercentage(){
        if(targetAmount.compareTo(BigDecimal.ZERO) == 0){
            return 0.0;
        }
        if (currentAmount == null) return 0.0;
        return currentAmount.divide(targetAmount, 2, RoundingMode.HALF_UP).doubleValue() * 100;
    }

    public BigDecimal getSuggestedMonthlyDeposit() {
        if (remainingPay == null || remainingPay.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(initDate, deadline);
        if (daysBetween < 30) {
            return remainingPay;
        }
        BigDecimal months = BigDecimal.valueOf(daysBetween)
                .divide(BigDecimal.valueOf(30), 4, RoundingMode.HALF_UP);

        return remainingPay.divide(months, 2, RoundingMode.HALF_UP);
    }

    @PrePersist
    public void prePersist(){
        this.remainingPay = this.targetAmount;
    }

}
