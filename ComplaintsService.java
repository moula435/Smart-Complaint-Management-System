package com.smartcomplaint.Service;

import com.smartcomplaint.Entities.Complaints;
import java.util.List;

public interface ComplaintsService {

        Complaints submitComplaint(Complaints complaint);
        Complaints getComplaintById(int complaintId);
        List<Complaints> getAllComplaints();
        List<Complaints> getComplaintsByUser(int userId);
        List<Complaints> getComplaintsByStatus(String status);
        List<Complaints> getComplaintsByAdmin(int adminId);
        Complaints updateComplaint(Complaints complaint);
        Complaints assignComplaintToAdmin(int complaintId, int adminId);
        Complaints updateComplaintStatus(int complaintId, String status);
        void deleteComplaint(int complaintId);
    
    
}