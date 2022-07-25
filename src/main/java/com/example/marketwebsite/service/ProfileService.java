package com.example.marketwebsite.service;

import com.example.marketwebsite.converter.AddressConverter;
import com.example.marketwebsite.converter.ProfileConverter;
import com.example.marketwebsite.dto.profile.*;
import com.example.marketwebsite.entity.AddressEntity;
import com.example.marketwebsite.entity.ProfileEntity;
import com.example.marketwebsite.enums.ProfileStatus;
import com.example.marketwebsite.exception.ProfileNotFoundException;
import com.example.marketwebsite.exception.ServerBadRequestException;
import com.example.marketwebsite.repository.AddressRepository;
import com.example.marketwebsite.repository.ProfileRepository;
import com.example.marketwebsite.util.JwtTokenUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // ======*****===== USER ====******=====
    public ProfileDetailDto getDetail(Integer userId) {
        return ProfileConverter.toDto(getEntityById(userId));
    }

    private ProfileEntity getEntityById(Integer userId) {
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (!optional.isPresent()) {
            throw new ServerBadRequestException("Profile not found");
        }
        return optional.get();
    }

    public void updateUserDetail(Integer userId, ProfileUpdateDto dto) {
        ProfileEntity entity = getEntityById(userId);
        entity.setName(dto.getName());
        entity.setSurname(dto.getName());
        entity.setContact(dto.getContact());
        profileRepository.save(entity);
    }

    public void updateUserEmail(Integer userId, String email) {
        AuthDetails authDetails = new AuthDetails();
        authDetails.setId(userId);
        authDetails.setEmail(email);
        String jwt = jwtTokenUtil.generateAccessToken(userId, email);
        String link = "http://localhost:8080/profile/verification/" + jwt;
        try {
            mailSenderService.sendEmail(email,
                    "Click the link to confirm your account " + link);
        } catch (Exception e) {
            throw new ServerBadRequestException(("Email not delivered"));
        }
    }

    public String verification(String jwt) {
        Integer userId = Integer.parseInt(jwtTokenUtil.getUserId(jwt));
        String email = jwtTokenUtil.getUsername(jwt);
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (!optional.isPresent()) {
            throw new ProfileNotFoundException("Profile not found");
        }
        ProfileEntity entity = optional.get();
        entity.setEmail(email);
        return "Successful verified";
    }


    //======================*****=============


    public void create(ProfileCreateDto dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new ServerBadRequestException("Email already exist");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(DigestUtils.md5Hex(dto.getPassword()));
        entity.setContact(dto.getContact());

        AddressEntity addressEntity = AddressConverter.toEntity(dto.getAddress());
        addressRepository.save(addressEntity);
        entity.setAddress(addressEntity);

        entity.setCreatedDate(LocalDateTime.now());

        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setRole(dto.getRole());

        profileRepository.save(entity);

        ProfileConverter.toDto(entity);

    }

    public ProfileDetailDto getById(Integer id) {
        return ProfileConverter.toDto(getEntityById(id));
    }

    public void update(Integer id, ProfileUpdateDto dto){
        ProfileEntity entity = getEntityById(id);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setContact(dto.getContact());
        profileRepository.save(entity);
    }


    public void changeStatus(Integer id, ProfileStatus status){
        getEntityById(id);
        profileRepository.changeStatus(status, id);
    }

    public List<ProfileDetailDto> getList(){
        return profileRepository.findAll().stream()
                .map(ProfileConverter::toDto)
                .collect(Collectors.toList());
    }

    public Page<ProfileDetailDto> getPaginationList(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        Page<ProfileEntity> paging = profileRepository.findAll(pageable);
        return paging.map(ProfileConverter::toDto);
    }

    public Page<ProfileDetailDto> filter(ProfileFilterDto filterDto){
        String sortBy = filterDto.getSortBy();
        if (sortBy == null || sortBy.isEmpty()){
            sortBy="createdDate";
        }
        Pageable pageable = PageRequest.of(filterDto.getPage(), filterDto.getSize(), filterDto.getDirection(), sortBy);
        List<Predicate> predicateList = new ArrayList<>();
        Specification<ProfileEntity> specification = (root, query, criteriaBuilder) ->{
            if (filterDto.getName() != null){
                predicateList.add(criteriaBuilder.equal(root.get("name"), filterDto.getName()));
            }
            if (filterDto.getSurname() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("surname"), filterDto.getSurname()));
            }
            if (filterDto.getEmail() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("email"), filterDto.getEmail()));
            }
            if (filterDto.getContact() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("contact"), filterDto.getContact()));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
        Page<ProfileEntity> paging = profileRepository.findAll(specification, pageable);
        List<ProfileDetailDto> response = new ArrayList<>();

        paging.forEach(profileEntity -> {
            response.add(ProfileConverter.toDto(profileEntity));
        });
        return paging.map(ProfileConverter::toDto);

    }



}
