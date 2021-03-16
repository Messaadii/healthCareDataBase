package com.healthCare.healthCareDataBase.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.healthCare.healthCareDataBase.Model.Image;
import com.healthCare.healthCareDataBase.Repository.ImageRepository;

import dtos.OneString;

@RestController
@CrossOrigin
@RequestMapping(value="/image")
public class ImageResource {
	
	@Autowired
	ImageRepository imageRepository;

	@PostMapping("/upload")
	public String uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {

		Image img = new Image(file.getOriginalFilename(), file.getContentType(),
				compressBytes(file.getBytes()));
		
		if(imageRepository.existsByImageName(file.getOriginalFilename())) {
			imageRepository.UpdateByImageName(img.getImageName(),img.getImageType(),img.getPicByte());
			return "imageUpdated";
		}else {
			imageRepository.save(img);
			return "imageUpdated";
		}
	}

	@GetMapping(path = { "/get/{imageName}" })
	public Image getImage(@PathVariable("imageName") String imageName) throws IOException {
		if(imageRepository.existsByImageName(imageName)){
			final Optional<Image> retrievedImage = imageRepository.findByImageName(imageName);
			Image img = new Image(retrievedImage.get().getImageName(), retrievedImage.get().getImageType(),
					decompressBytes(retrievedImage.get().getPicByte()));
			return img;
		}else
			return null;
	}

	@PostMapping(value="checkIfDocumentExist")
	public boolean checkIfDocumentExist(@RequestBody final OneString oneString) {
		return imageRepository.existsByImageName(oneString.getOne());
	}
	
	@DeleteMapping(value="deleteByImageName/{imageName}")
	@Transactional
	public long deleteByImageName(@PathVariable("imageName") String imageName) {
		return imageRepository.deleteByImageName(imageName);
	}
	
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}

		return outputStream.toByteArray();
	}

	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}
	
}
