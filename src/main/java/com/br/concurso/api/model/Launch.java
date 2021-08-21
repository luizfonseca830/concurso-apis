package com.br.concurso.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "launch")
public class Launch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @NotNull
    private String description;

    @NotNull
    @Column(name = "due_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dueDate;

    @Column(name = "payment_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate paymentDate;

    @NotNull
    private BigDecimal amount;

    private String observation;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeLanch type;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_code")
    private Category category;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "people_code")
    private People people;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public TypeLanch getType() {
        return type;
    }

    public void setType(TypeLanch type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Launch launch = (Launch) o;
        return code.equals(launch.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
