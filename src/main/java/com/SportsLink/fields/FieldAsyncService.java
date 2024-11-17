package com.SportsLink.fields;

import com.SportsLink.followers.FollowersRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FieldAsyncService {
    private final FieldRepository fieldRepository;

    @Async
    public void incrementFieldFollowers(int fieldId) {
        fieldRepository.incrementFieldFollowers(fieldId);
    }

    @Async
    public void decrementFieldFollowers(int fieldId) {
        fieldRepository.decrementFieldFollowers(fieldId);
    }
}
