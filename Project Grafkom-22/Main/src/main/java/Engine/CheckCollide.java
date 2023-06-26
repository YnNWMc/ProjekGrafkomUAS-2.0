package Engine;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class CheckCollide {
    public static boolean checkCollision(List<Vector3f> v1, List<Vector3f> v2) {
        float minX1 = Float.POSITIVE_INFINITY, minY1 = Float.POSITIVE_INFINITY, minZ1 = Float.POSITIVE_INFINITY;
        float maxX1 = Float.NEGATIVE_INFINITY, maxY1 = Float.NEGATIVE_INFINITY, maxZ1 = Float.NEGATIVE_INFINITY;

        float minX2 = Float.POSITIVE_INFINITY, minY2 = Float.POSITIVE_INFINITY, minZ2 = Float.POSITIVE_INFINITY;
        float maxX2 = Float.NEGATIVE_INFINITY, maxY2 = Float.NEGATIVE_INFINITY, maxZ2 = Float.NEGATIVE_INFINITY;

        for (Vector3f vertex : v1) {
            minX1 = Math.min(minX1, vertex.x);
            minY1 = Math.min(minY1, vertex.y);
            minZ1 = Math.min(minZ1, vertex.z);

            maxX1 = Math.max(maxX1, vertex.x);
            maxY1 = Math.max(maxY1, vertex.y);
            maxZ1 = Math.max(maxZ1, vertex.z);
        }

        for (Vector3f vertex : v2) {
            minX2 = Math.min(minX2, vertex.x);
            minY2 = Math.min(minY2, vertex.y);
            minZ2 = Math.min(minZ2, vertex.z);

            maxX2 = Math.max(maxX2, vertex.x);
            maxY2 = Math.max(maxY2, vertex.y);
            maxZ2 = Math.max(maxZ2, vertex.z);
        }

        // Memeriksa tumbukan pada sumbu x
        if (maxX1 < minX2 || minX1 > maxX2) {
            return false; // Terjadi pemisahan pada sumbu x
        }

        // Memeriksa tumbukan pada sumbu y
        if (maxY1 < minY2 || minY1 > maxY2) {
            return false; // Terjadi pemisahan pada sumbu y
        }

        // Memeriksa tumbukan pada sumbu z
        if (maxZ1 < minZ2 || minZ1 > maxZ2) {
            return false; // Terjadi pemisahan pada sumbu z
        }

        return true; // Terdapat tumbukan pada semua sumbu
    }

}
