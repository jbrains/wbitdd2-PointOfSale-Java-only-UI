package ca.jbrains.pos;

import ca.jbrains.pos.domain.CatalogEntry;

import java.util.List;

public record Purchase(int total, List<CatalogEntry> items) {
}
