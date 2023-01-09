package com.raimon.dogfriendly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raimon.dogfriendly.entity.FacturaEntity;
import com.raimon.dogfriendly.exception.ResourceNotFoundException;
import com.raimon.dogfriendly.exception.ResourceNotModifiedException;
import com.raimon.dogfriendly.repository.FacturaRepository;

@Service
public class FacturaService {

    @Autowired
    FacturaRepository oFacturaRepository;

    @Autowired
    AuthService oAuthService;

    public void validate(Long id) {
        if (!oFacturaRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public Long count() {
        // oAuthService.OnlyAdmins();
        return oFacturaRepository.count();
    }


    public FacturaEntity get(Long id) {
        return oFacturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team with id: " + id + " not found"));
    }

    public Long create(FacturaEntity oNewFacturaEntity) {
        oAuthService.OnlyAdmins();
        //validate(oNewUsuarioEntity);
        oNewFacturaEntity.setId(0L);
        return oFacturaRepository.save(oNewFacturaEntity).getId();
    }


    public Long update(FacturaEntity oFacturaEntity) {
        validate(oFacturaEntity.getId());
        // oAuthService.OnlyAdmins();
        FacturaEntity oOldFacturaEntity = oFacturaRepository.getReferenceById(oFacturaEntity.getId());
        return oFacturaRepository.save(oFacturaEntity).getId();
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        validate(id);
        oFacturaRepository.deleteById(id);
        if (oFacturaRepository.existsById(id)) {
            throw new ResourceNotModifiedException("can't remove register " + id);
        } else {
            return id;
        }
    }

}