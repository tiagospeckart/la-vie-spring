package com.moredevs.psychclinic.service;

import com.moredevs.psychclinic.models.dtos.AdminDTO;

public interface AdminService extends Service<AdminDTO> {
    public AdminDTO create(AdminDTO t);
    public AdminDTO save(AdminDTO t);


}
