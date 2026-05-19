package com.smartcomplaint.Service;

import com.smartcomplaint.Entities.StatusHistory;
import java.util.List;

public interface StatusHistoryService {

    // Create
    StatusHistory addStatusHistory(StatusHistory statusHistory);

    // Read
    StatusHistory getStatusHistoryById(int historyId);
    List<StatusHistory> getHistoryByComplaint(int complaintId);
    List<StatusHistory> getHistoryByAdmin(int adminId);
}