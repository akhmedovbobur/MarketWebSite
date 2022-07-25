package com.example.marketwebsite.controller;

import com.example.marketwebsite.dto.profile.ProfileCreateDto;
import com.example.marketwebsite.dto.profile.ProfileDetailDto;
import com.example.marketwebsite.dto.profile.ProfileFilterDto;
import com.example.marketwebsite.dto.profile.ProfileUpdateDto;
import com.example.marketwebsite.enums.ProfileStatus;
import com.example.marketwebsite.service.ProfileService;
import com.example.marketwebsite.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    /* ======*****==== USER  =====****====== */

    @GetMapping("/detail")
    public ResponseEntity<?> getProfileDetail(HttpServletRequest request){
        Integer userId = SpringSecurityUtil.getUserId();
        ProfileDetailDto dto = profileService.getDetail(userId);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/detail")
    public ResponseEntity<?> updateDetail(@Valid@RequestBody ProfileUpdateDto dto){
        Integer userId = SpringSecurityUtil.getUserId();
        profileService.updateUserDetail(userId, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/email")
    public ResponseEntity<?> updateEmail(@RequestParam("email") String email,
                                         HttpServletRequest request){
        Integer userId=SpringSecurityUtil.getUserId();
        profileService.updateUserEmail(userId, email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verification/{jwt}")
    public ResponseEntity<?> verification(@PathVariable("jwt") String jwt){
        String result = profileService.verification(jwt);
        return ResponseEntity.ok(result);
    }

    //========****===== ADMIN =====*******======

    @PostMapping("/admin/create")
    public ResponseEntity<?> create(@RequestBody ProfileCreateDto dto){
        profileService.create(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        ProfileDetailDto result = profileService.getById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody ProfileUpdateDto dto,
                                    @PathVariable("id") Integer id) {
        profileService.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/admin/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Integer id,
                                          @RequestParam ProfileStatus status) {
        profileService.changeStatus(id, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/adm/list")
    public ResponseEntity<List<ProfileDetailDto>> getList() {
        List<ProfileDetailDto> list = profileService.getList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/adm/paging")
    public ResponseEntity<Page<ProfileDetailDto>> getList(@RequestParam("page") Integer page,
                                                          @RequestParam("size") Integer size) {
        Page<ProfileDetailDto> list = profileService.getPaginationList(page, size);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/adm/filter")
    public ResponseEntity<Page<ProfileDetailDto>> filter(@RequestBody ProfileFilterDto dto) {
        Page<ProfileDetailDto> result = profileService.filter(dto);
        return ResponseEntity.ok(result);
    }


}
