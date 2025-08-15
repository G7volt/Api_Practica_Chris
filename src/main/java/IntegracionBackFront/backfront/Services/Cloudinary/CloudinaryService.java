package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Service
public class CloudinaryService {

    //1- Definir el tamaño de las imagenes en megabytes(MB)
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    //2- Definir las extenciones permitidas
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png"};

    //3- Atributo Cloudinary
    private final Cloudinary cloudinary;

    //4- Crear constructor de Cloudinary para inyeccion de dependencias
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        validateImage(file);
    }

    private void validateImage(MultipartFile file) {
        //1- verificar is el archivo esta vacio
        if (file.isEmpty()){
            throw new IllegalArgumentException("El archivo esta vacio");
        }

        //2-Verificar si el tamaño excede el limite permitido
        if (file.getSize() > MAX_FILE_SIZE){
            throw new IllegalArgumentException("El tamaño del archivo excede el tamaño permitido");
        }

        //3- Verificar la extension del archivo
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null){
            throw new IllegalArgumentException("Nombre de archivo invalido");
        }

        //4- Extraer y validar la extencion del archivo
        String Extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(Extension)){
            throw new IllegalArgumentException("Solo se admiten archivos JPG, JPEG, PNG");
        }

        //5-
        if (!file.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("El Archivo debe ser una imagen valida")
        }
    }

}
