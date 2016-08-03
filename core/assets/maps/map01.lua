return {
  version = "1.1",
  luaversion = "5.1",
  tiledversion = "0.16.2",
  orientation = "orthogonal",
  renderorder = "right-down",
  width = 16,
  height = 16,
  tilewidth = 32,
  tileheight = 32,
  nextobjectid = 1,
  properties = {},
  tilesets = {
    {
      name = "tiles",
      firstgid = 1,
      tilewidth = 32,
      tileheight = 32,
      spacing = 0,
      margin = 0,
      image = "tiles.png",
      imagewidth = 256,
      imageheight = 96,
      tileoffset = {
        x = 0,
        y = 0
      },
      properties = {},
      terrains = {},
      tilecount = 24,
      tiles = {}
    },
    {
      name = "OriginalRIP_zps84dc6f92_193",
      firstgid = 25,
      tilewidth = 16,
      tileheight = 29,
      spacing = 0,
      margin = 0,
      image = "../../../OriginalRIP_zps84dc6f92_193.png",
      imagewidth = 64,
      imageheight = 29,
      tileoffset = {
        x = 0,
        y = 0
      },
      properties = {},
      terrains = {},
      tilecount = 4,
      tiles = {}
    }
  },
  layers = {
    {
      type = "tilelayer",
      name = "groundlayer",
      x = 0,
      y = 0,
      width = 16,
      height = 16,
      visible = true,
      opacity = 1,
      offsetx = 0,
      offsety = 0,
      properties = {},
      encoding = "base64",
      compression = "zlib",
      data = "eJy9kNEJwCAQQyPFTz+sHaijOIqjOIqjdJQGRChitWdLPwIK93LJKQDqBznAL0AsckKeDLaL+E89/1pmwNf+I93x3JOWnKUr5o0tvvaV3sd0btC7vaR/K6N70JvZ/BcdR7ymLGdWIPAdpDzZWLrS45jY721mdz3Bv+0v4U+Z6Sir"
    },
    {
      type = "tilelayer",
      name = "solidlayer",
      x = 0,
      y = 0,
      width = 16,
      height = 16,
      visible = true,
      opacity = 1,
      offsetx = 0,
      offsety = 0,
      properties = {
        ["collidable"] = "true"
      },
      encoding = "base64",
      compression = "zlib",
      data = "eJzFk1EKwCAMQ8s+BHuD3f+i88Ow0KVuZYM98ENtao21m1n/YGxjuJ34XMO+AnsetKBR3EoP9iQuY1Xbn3p4CQ957nb1/s3Z0Fe9u3vbSg3IB/iuuLuqj/uHtZGsv5ReEXs76pGjiZinev4H7C3eX1H5n7G2NtcPi6EDvw=="
    }
  }
}
