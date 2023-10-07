package com.moredevs.psychclinic.services;

import com.moredevs.psychclinic.models.dtos.AdminCreateDTO;
import com.moredevs.psychclinic.models.dtos.AdminDTO;
import com.moredevs.psychclinic.models.dtos.AdminGetDTO;

import java.util.List;

public interface AdminService {
    public AdminDTO create(AdminCreateDTO adminCreateDTO);
    public AdminDTO save(AdminCreateDTO adminSaveDTO);
    public AdminGetDTO findById(Integer id);
    public List<AdminGetDTO> listAll();
    public AdminDTO update(AdminDTO adminUpdateDTO);
    public void deleteById(Integer id);

}
