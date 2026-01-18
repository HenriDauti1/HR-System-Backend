package com.hr_system.service;

import com.hr_system.entity.Attendance;
import com.hr_system.repository.AttendanceRepository;
import com.hr_system.responses.AttendanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Transactional(readOnly = true)
    public List<AttendanceResponse> getAllAttendances() {
        List<Attendance> attendances = attendanceRepository.findAllByEmployee_IsActiveTrue();


        return attendances.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    private AttendanceResponse convertToResponse(Attendance attendance) {
        AttendanceResponse response = new AttendanceResponse();
        response.setAttendanceId(attendance.getAttendanceId());
        response.setEmployeeId(attendance.getEmployee().getEmployeeId());
        response.setEmployeeName(attendance.getEmployee().getFirstName() + " " + attendance.getEmployee().getLastName());
        response.setAttendanceDate(attendance.getAttendanceDate());
        response.setCheckIn(attendance.getCheckIn());
        response.setCheckOut(attendance.getCheckOut());
        return response;
    }
}