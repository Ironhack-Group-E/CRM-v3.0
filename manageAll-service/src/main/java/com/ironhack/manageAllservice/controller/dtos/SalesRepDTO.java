package com.ironhack.manageAllservice.controller.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class SalesRepDTO {
    private Integer id;
    @NotEmpty
    @Pattern(regexp = "^[ÁÉÍÓÚA-ZÑ]?[a-záéíóúñ]+(\\s+[ÁÉÍÓÚA-ZÑ]?[a-záéíóúñ]+)*${1,31}")
    private String name;

    /*@OneToMany(mappedBy = "salesRep")
    List<Lead> leads;
    @OneToMany(mappedBy = "salesRep")
    List<Opportunity> opportunities;*/

    public SalesRepDTO() {
    }

    public SalesRepDTO(Integer id, @NotEmpty String name) {
        this.id = id;
        this.name = name;
    }

    public SalesRepDTO(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
