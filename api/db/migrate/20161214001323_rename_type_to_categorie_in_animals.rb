class RenameTypeToCategorieInAnimals < ActiveRecord::Migration[5.0]
  def change
    rename_column :animals, :type, :categorie
  end
end
