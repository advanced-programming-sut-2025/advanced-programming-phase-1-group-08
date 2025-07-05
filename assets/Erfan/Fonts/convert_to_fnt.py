import json
from PIL import Image

# Load JSON and image
with open('SpriteFont1.json', 'r', encoding='utf-8') as f:
    data = json.load(f)
img = Image.open('SpriteFont1.png')
scaleW, scaleH = img.size

# Prepare BMFont .fnt content
lines = []

# Header info
face_name = "SpriteFont1"
size = data.get("LineSpacing", 16)
lines.append(f'info face="{face_name}" size={size} bold=0 italic=0 charset="" unicode=1 stretchH=100 smooth=1 aa=1 padding=0,0,0,0 spacing={int(data.get("Spacing",1))},{int(data.get("Spacing",1))}')

# Common line
lineHeight = data.get("LineSpacing", size)
base = lineHeight
pages = 1
lines.append(f'common lineHeight={lineHeight} base={base} scaleW={scaleW} scaleH={scaleH} pages={pages} packed=0')

# Page definition
lines.append(f'page id=0 file="SpriteFont1.png"')

# Chars count
glyphs = data.get("Glyphs", {})
lines.append(f'chars count={len(glyphs)}')

# Each char
for char, info in glyphs.items():
    char_id = ord(info["Character"])
    bx = info["BoundsInTexture"]["X"]
    by = info["BoundsInTexture"]["Y"]
    bw = info["BoundsInTexture"]["Width"]
    bh = info["BoundsInTexture"]["Height"]
    xoffset = info["Cropping"]["X"]
    yoffset = info["Cropping"]["Y"]
    xadvance = int(info.get("WidthIncludingBearings", info.get("Width", bw)))
    lines.append(
        f'char id={char_id}   x={bx}    y={by}    width={bw}    height={bh}    '
        f'xoffset={xoffset}    yoffset={yoffset}    xadvance={xadvance}    page=0    chnl=0'
    )

# Write .fnt file
with open('SpriteFont1.fnt', 'w', encoding='utf-8') as f:
    f.write('\n'.join(lines))

print("Generated SpriteFont1.fnt successfully.")
