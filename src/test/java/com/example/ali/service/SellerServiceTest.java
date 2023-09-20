package com.example.ali.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.ali.dto.MessageDataResponseDto;
import com.example.ali.dto.SellerSignupRequestDto;
import com.example.ali.dto.StoreRequestDto;
import com.example.ali.dto.StoreResponseDto;
import com.example.ali.entity.Seller;
import com.example.ali.entity.SellerWallet;
import com.example.ali.repository.SellerRepository;
import com.example.ali.repository.SellerWalletRepository;
import com.example.ali.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
class SellerServiceTest {

    @InjectMocks
    private SellerService sellerService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private SellerWalletRepository sellerWalletRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("셀러의 회원가입 성공")
    @Transactional
    public void signup(){

        // given
        SellerSignupRequestDto requestDto = new SellerSignupRequestDto("testSeller", "1234",
            "storeName", "storeInfo");
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        SellerWallet sellerWallet = new SellerWallet();
        Seller seller = new Seller(requestDto, password, sellerWallet);

        // when
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn(password);

        when(userRepository.findByUsername("testSeller")).thenReturn(Optional.empty());
        when(sellerRepository.findByUsername("testSeller")).thenReturn(Optional.empty());
        when(sellerRepository.findByStoreName("testSeller")).thenReturn(Optional.empty());

        when(sellerWalletRepository.save(any(SellerWallet.class))).thenReturn(sellerWallet);
        when(sellerRepository.save(any(Seller.class))).thenReturn(seller);

        // 테스트 실행
        ResponseEntity<?> responseEntity = sellerService.signup(requestDto);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Test
    @DisplayName("상품 등록한 모든 셀러의 정보 조회 성공")
    @Transactional(readOnly = true)
    public void getStores(){
        //given
        List<Seller> storeList = new ArrayList<>();
        storeList.add(new Seller("Store1", "Info1"));
        storeList.add(new Seller("Store2", "Info2"));

        //when
        when(sellerRepository.findAll()).thenReturn(storeList);

        ResponseEntity<?> responseEntity = sellerService.getStores();

        //then
        List<StoreResponseDto> responseBody = (List<StoreResponseDto>) responseEntity.getBody();

        assertEquals(2, responseBody.size()); // 2개의 셀러 정보가 있어야 함
        assertEquals("Store1", responseBody.get(0).getStoreName());
        assertEquals("Info1", responseBody.get(0).getInfo());
        assertEquals("Store2", responseBody.get(1).getStoreName());
        assertEquals("Info2", responseBody.get(1).getInfo());
    }

    @Test
    @DisplayName("해당 셀러 상점 수정하기 성공")
    @Transactional
    public void updateStore(){
        // given
        StoreRequestDto requestDto = new StoreRequestDto();
        requestDto.setSellerId(1L);
        requestDto.setInfo("Info1 update");
        requestDto.setStoreName("Store1 update");

        Seller seller = new Seller("Store1", "Info1");
        seller.setId(1L);

        // when
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
        ResponseEntity<?> responseEntity = sellerService.updateStore(requestDto);

        // then
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode()); // HTTP 상태 코드가 ACCEPTED 여야 함

        MessageDataResponseDto responseBody = (MessageDataResponseDto) responseEntity.getBody();
        StoreResponseDto updatedStore = (StoreResponseDto) responseBody.getData();

        assertEquals("Store1 update", updatedStore.getStoreName());
        assertEquals("Info1 update", updatedStore.getInfo());

    }

    @Test
    @DisplayName("해당 셀러 상점 삭제하기 성공")
    @Transactional
    public void deleteStore(){

        // given
        Seller seller = new Seller("Store1", "Info1");
        seller.setId(1L); // 가짜 셀러 ID 설정

        // when
        doNothing().when(sellerRepository).delete(seller);
        ResponseEntity<?> responseEntity = sellerService.deleteStore(seller);
        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // 반환 메세지 검증은 생략
    }


}