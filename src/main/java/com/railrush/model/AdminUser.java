package com.railrush.model;

/**
 * AdminUser extending User to demonstrate Hierarchical Inheritance (Exp 03).
 * Represents railway operations and station master administrators.
 */
public class AdminUser extends User {
    private String department;
    private String employeeId;
    private boolean canManageSchedules;

    public AdminUser() {
        super();
        this.role = "ADMIN";
        this.canManageSchedules = true;
    }

    public AdminUser(int userId, String fullName, String email, String passwordHash, String phoneNumber, String department, String employeeId) {
        super(userId, fullName, email, passwordHash, "ADMIN", phoneNumber);
        this.department = department;
        this.employeeId = employeeId;
        this.canManageSchedules = true;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public boolean isCanManageSchedules() {
        return canManageSchedules;
    }

    public void setCanManageSchedules(boolean canManageSchedules) {
        this.canManageSchedules = canManageSchedules;
    }
}
