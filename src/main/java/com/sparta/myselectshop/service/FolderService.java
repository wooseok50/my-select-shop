package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.FolderResponseDto;
import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.repository.FolderRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    public void addFolders(List<String> folderNames, User user) {

        List<Folder> existfolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);
        List<Folder> folderList = new ArrayList();

        for (String folderName : folderNames) {
            if (!isExistFolderName(folderName, existfolderList)) {
                Folder folder = new Folder(folderName, user);
                folderList.add(folder);
            } else {
                throw new IllegalArgumentException("폴더명이 중복되었습니다");
            }
        }
        folderRepository.saveAll(folderList);
    }

    public List<FolderResponseDto> getFolders(User user) {

        List<Folder> forderList = folderRepository.findAllByUser(user);
        List<FolderResponseDto> responseDtoList = new ArrayList<>();

        for (Folder folder : forderList) {
            responseDtoList.add(new FolderResponseDto(folder));
        }
        return responseDtoList;
    }

    private boolean isExistFolderName(String folderName, List<Folder> existfolderList) {
        for (Folder existFolder : existfolderList) {
            if (folderName.equals(existFolder.getName())) {
                return true;
            }
        }
        return false;
    }
}