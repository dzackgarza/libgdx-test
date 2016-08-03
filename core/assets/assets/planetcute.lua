return {
  version = "1.1",
  luaversion = "5.1",
  tiledversion = "0.14.1",
  orientation = "orthogonal",
  renderorder = "right-up",
  width = 12,
  height = 18,
  tilewidth = 101,
  tileheight = 41,
  nextobjectid = 1,
  properties = {},
  tilesets = {
    {
      name = "planetcute",
      firstgid = 1,
      tilewidth = 101,
      tileheight = 171,
      spacing = 0,
      margin = 0,
      image = "planetcute.png",
      imagewidth = 1010,
      imageheight = 1010,
      tileoffset = {
        x = 0,
        y = 0
      },
      properties = {},
      terrains = {},
      tilecount = 50,
      tiles = {}
    }
  },
  layers = {
    {
      type = "tilelayer",
      name = "Tile Layer 1",
      x = 0,
      y = 0,
      width = 12,
      height = 18,
      visible = true,
      opacity = 1,
      properties = {},
      encoding = "lua",
      data = {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 34, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 38, 38, 38,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 35, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        4, 0, 25, 17, 0, 0, 0, 17, 13, 0, 0, 0,
        17, 17, 17, 1, 0, 0, 0, 1, 17, 17, 41, 0,
        1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 17, 15,
        1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 17,
        1, 1, 1, 1, 44, 44, 44, 1, 1, 1, 1, 1
      }
    }
  }
}
