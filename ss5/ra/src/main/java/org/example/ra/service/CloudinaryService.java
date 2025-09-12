package org.example.ra.service;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public String avatar(MultipartFile file) throws IOException {
        Map<String,Object> avatar = cloudinary.uploader().upload(file.getBytes(),Map.of("folder","avatars"));
        return String.valueOf(avatar.get("secure_url"));
    }
}
