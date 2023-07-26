package com.kkoch.admin.api.service.auction;

import com.kkoch.admin.api.service.auction.dto.AddAuctionArticleDto;
import com.kkoch.admin.domain.auction.Auction;
import com.kkoch.admin.domain.auction.AuctionArticle;
import com.kkoch.admin.domain.auction.repository.AuctionArticleRepository;
import com.kkoch.admin.domain.auction.repository.AuctionRepository;
import com.kkoch.admin.domain.plant.Plant;
import com.kkoch.admin.domain.plant.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Transactional
public class AuctionArticleService {

    private final AuctionArticleRepository auctionArticleRepository;
    private final AuctionRepository auctionRepository;
    private final PlantRepository plantRepository;

    public Long addAuctionArticle(Long plantId, Long auctionId, AddAuctionArticleDto dto) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 경매 일정"));
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 식물입니다."));
        AuctionArticle auctionArticle = AuctionArticle.toEntity(
                plant,
                auction,
                dto.getAuctionNumber(),
                dto.getGrade(),
                dto.getCount(),
                dto.getRegion(),
                dto.getShipper(),
                dto.getStartPrice());
        AuctionArticle savedAuctionArticle = auctionArticleRepository.save(auctionArticle);
        return savedAuctionArticle.getId();
    }
}
