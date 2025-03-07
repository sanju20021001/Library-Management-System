package com.project.library.service;

import com.project.library.model.Reserve;
import com.project.library.repository.ReserveRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReserveService {
    
    @Autowired
    private ReserveRepository reserveRepository;
    
    public void reserveBook(Reserve reserve) {
        reserveRepository.save(reserve);
    }
    
    public List<Reserve> getAllReservations() {
        return reserveRepository.findAll();
    }
    
    public boolean isReserved(int id) {
        boolean isReserved = false;
        List<Reserve> all = reserveRepository.findAll();
        
        for (Reserve r : all) {
            if (r.getUid() == id) {
                isReserved = true;
                break;
            }
        }
        
        return isReserved;
    }
    
    public int getReservationId(int userId) {
        int reservationId = 0;
        List<Reserve> all = reserveRepository.findAll();
        
        for (Reserve r : all) {
            if (r.getUid() == userId) {
                reservationId = r.getId();
                break;
            }
        }
        
        return reservationId;
    }
    
    public void deleteReservation(int id) {
        reserveRepository.deleteById(id);
    }
}
