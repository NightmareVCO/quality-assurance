package org.example.entities;

//FULL_TIME, PART_TIME

public enum EmployeeType {
    FULL_TIME("Full Time"),
    PART_TIME("Part Time");

    private final String description;

    EmployeeType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
