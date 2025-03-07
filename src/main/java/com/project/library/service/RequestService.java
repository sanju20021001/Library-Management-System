package com.project.library.service;

import com.project.library.model.Request;
import com.project.library.repository.RequestRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
    
    @Autowired
    private RequestRepository requestRepository;
    
    public void request(Request request) {
        requestRepository.save(request);
    }
    
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }
}
