package com.bombombom.devs.external.video.service;

import com.bombombom.devs.core.exception.ErrorCode;
import com.bombombom.devs.core.exception.NotFoundException;
import com.bombombom.devs.external.video.service.result.dto.VideoResult;
import com.bombombom.devs.study.model.Video;
import com.bombombom.devs.study.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;


    public VideoResult getVideo(Long videoId) {
        Video video = videoRepository.findById(videoId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        return VideoResult.fromEntity(video);
    }

}
