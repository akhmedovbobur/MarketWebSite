package com.example.marketwebsite.service;

import com.example.marketwebsite.converter.AddressConverter;
import com.example.marketwebsite.dto.AddressDto;
import com.example.marketwebsite.dto.AuthDto;
import com.example.marketwebsite.dto.RegistrationDto;
import com.example.marketwebsite.dto.profile.ProfileDetailDto;
import com.example.marketwebsite.entity.AddressEntity;
import com.example.marketwebsite.entity.ProfileEntity;
import com.example.marketwebsite.enums.ProfileRole;
import com.example.marketwebsite.enums.ProfileStatus;
import com.example.marketwebsite.exception.ProfileNotFoundException;
import com.example.marketwebsite.exception.ServerBadRequestException;
import com.example.marketwebsite.repository.AddressRepository;
import com.example.marketwebsite.repository.ProfileRepository;
import com.example.marketwebsite.util.JwtTokenUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthorizationService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${server.url}")
    private String serverUrl;

    public ProfileDetailDto authorization(AuthDto dto) {
        String email = dto.getEmail();
        String password = DigestUtils.md5Hex(dto.getPassword());
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndPassword(email, password);
        if (!optional.isPresent()) {
            throw new ProfileNotFoundException("Login or password INVALID");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new ProfileNotFoundException("Not Active");
        }

        String jwt = jwtTokenUtil.generateAccessToken(entity.getId(), email);

        ProfileDetailDto responseDto = new ProfileDetailDto();
        responseDto.setToken(jwt);
        responseDto.setName(entity.getName());
        responseDto.setSurname(entity.getSurname());
        responseDto.setContact(entity.getContact());

        return responseDto;
    }

    public String registration(RegistrationDto dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new ServerBadRequestException("Email already exist");
        }
        ProfileEntity entity = new ProfileEntity();
        AddressDto addressDto = dto.getAddress();
        AddressEntity addressEntity = AddressConverter.toEntity(addressDto);
        addressRepository.save(addressEntity);

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setContact(dto.getContact());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setAddress(addressEntity);
        entity.setRole(ProfileRole.USER);
        entity.setStatus(ProfileStatus.INACTIVE);
        entity.setPassword(DigestUtils.md5Hex(dto.getPassword()));

        this.profileRepository.save(entity);

        String jwt = jwtTokenUtil.generateAccessToken(entity.getId(), entity.getEmail());
        String link = "http://localhost:8080/login/verification/" + jwt;

        try {
            mailSenderService.sendEmail(dto.getEmail(),
                    "Click the link to confirm your account " + link);
        } catch (Exception e) {
            profileRepository.delete(entity);
            addressRepository.delete(addressEntity);
            throw new ServerBadRequestException("Email not delivered");
        }
        return "Go to email and verification";
    }

    public String verification(String jwt) {
        Integer profileID = Integer.parseInt(jwtTokenUtil.getUserId(jwt));
        Optional<ProfileEntity> optional = profileRepository.findById(profileID);
        if (!optional.isPresent()) {
            throw new ProfileNotFoundException("Invalid id");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.INACTIVE)) {
            throw new ServerBadRequestException("Wrong status");
        }
        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity);
        return "Successful verified";
    }
}
