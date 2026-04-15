import png
import os

# Define a 16x16 image for Serrated Catalyst
# Style: Dark gray/metallic with some dark red/brown "blood" or "sharp" bits
# 0: Transparent, 1: Dark Gray, 2: Gray, 3: Light Gray, 4: Dark Red, 5: Black

palette = [
    (0, 0, 0, 0),        # 0: Transparent
    (60, 60, 60, 255),    # 1: Dark Gray
    (100, 100, 100, 255), # 2: Gray
    (150, 150, 150, 255), # 3: Light Gray
    (120, 20, 20, 255),   # 4: Dark Red
    (20, 20, 20, 255)     # 5: Black
]

data = [
    [0,0,0,0,0,5,5,5,5,0,0,0,0,0,0,0],
    [0,0,0,0,5,2,2,3,2,5,0,0,0,0,0,0],
    [0,0,0,5,2,3,3,2,3,2,5,0,0,0,0,0],
    [0,0,0,5,2,3,2,2,2,1,5,0,0,0,0,0],
    [0,0,5,2,3,2,1,5,1,1,2,5,0,0,0,0],
    [0,5,2,2,2,1,5,4,5,1,2,2,5,0,0,0],
    [0,5,2,2,1,5,4,4,4,5,1,2,5,0,0,0],
    [0,5,3,2,1,5,4,4,4,5,1,2,5,0,0,0],
    [0,5,2,1,1,1,5,4,5,1,1,2,5,0,0,0],
    [0,0,5,2,1,1,1,5,1,1,1,5,0,0,0,0],
    [0,0,0,5,2,1,1,2,1,2,5,0,0,0,0,0],
    [0,0,0,5,2,2,2,2,2,5,0,0,0,0,0,0],
    [0,0,0,0,5,5,5,5,5,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
]

# Create the PNG
img_data = []
for row in data:
    for pixel in row:
        img_data.extend(palette[pixel])

path = 'src/main/resources/assets/catalyze_mod/textures/item/serrated_catalyst.png'
os.makedirs(os.path.dirname(path), exist_ok=True)

with open(path, 'wb') as f:
    w = png.Writer(16, 16, greyscale=False, alpha=True)
    w.write_array(f, img_data)

print(f"Created {path}")
