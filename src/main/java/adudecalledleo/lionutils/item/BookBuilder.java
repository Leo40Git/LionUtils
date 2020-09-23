package adudecalledleo.lionutils.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.*;

/**
 * Helper class for building written books.
 *
 * @since 6.0.0
 */
public final class BookBuilder {
    /**
     * Represents a page in a book.
     */
    public static final class Page {
        /**
         * The contents of the page.
         */
        public final List<Text> lines;

        private Page(Collection<Text> lines) {
            this.lines = new ArrayList<>(lines);
        }

        private Page(Text... lines) {
            this.lines = new ArrayList<>(lines.length);
            Collections.addAll(this.lines, lines);
        }

        private Page() {
            this.lines = new ArrayList<>();
        }
    }

    /**
     * Creates a new page.
     *
     * @param lines
     *         page contents
     * @return a new page that contains the specified lines
     */
    public static Page page(Collection<Text> lines) {
        return new Page(lines);
    }

    /**
     * Creates a new page.
     *
     * @param lines
     *         page contents
     * @return a new page that contains the specified lines
     */
    public static Page page(Text... lines) {
        return new Page(lines);
    }

    /**
     * Creates a new empty page.
     *
     * @return a new empty page
     */
    public static Page emptyPage() {
        return new Page();
    }

    private final String author;
    private final String title;
    private final List<Page> pages;
    private final List<Text> lore;
    private Style titleStyle;

    private BookBuilder(String author, String title) {
        this.author = author;
        this.title = title;
        pages = new ArrayList<>();
        lore = new ArrayList<>();
    }

    /**
     * Creates a new {@code BookBuilder}.
     *
     * @param author
     *         book author
     * @param title
     *         book title
     * @return a new builder instance
     */
    public static BookBuilder create(String author, String title) {
        Objects.requireNonNull(author, "author == null!");
        Objects.requireNonNull(title, "title == null!");
        return new BookBuilder(author, title);
    }

    /**
     * Adds a page to the resulting book.
     *
     * @param page
     *         page to add
     * @return this builder
     */
    public BookBuilder addPage(Page page) {
        pages.add(page);
        return this;
    }

    /**
     * Adds a new page to the resulting book.
     *
     * @param lines
     *         contents of page to add
     * @return this builder
     */
    public BookBuilder addPage(Collection<Text> lines) {
        return addPage(page(lines));
    }

    /**
     * Adds a new page to the resulting book.
     *
     * @param lines
     *         contents of page to add
     * @return this builder
     */
    public BookBuilder addPage(Text... lines) {
        return addPage(page(lines));
    }

    /**
     * Adds a new empty page to the resulting book.
     *
     * @return this builder
     */
    public BookBuilder addEmptyPage() {
        return addPage(emptyPage());
    }

    /**
     * Adds a line of lore to the resulting book.
     *
     * @param line
     *         line of lore to add
     * @return this builder
     */
    public BookBuilder addLore(Text line) {
        lore.add(line);
        return this;
    }

    /**
     * Add lines of lore to the resulting book.
     *
     * @param lines
     *         lines of lore to add
     * @return this builder
     */
    public BookBuilder addLore(Collection<Text> lines) {
        lore.addAll(lines);
        return this;
    }

    /**
     * Add lines of lore to the resulting book.
     *
     * @param lines
     *         lines of lore to add
     * @return this builder
     */
    public BookBuilder addLore(Text... lines) {
        Collections.addAll(lore, lines);
        return this;
    }

    /**
     * Sets the resulting book's title {@code Style}.
     *
     * @param titleStyle
     *         book title style
     * @return this builder
     */
    public BookBuilder setTitleStyle(Style titleStyle) {
        this.titleStyle = titleStyle;
        return this;
    }

    /**
     * Builds a new {@code ItemStack} representing the built book.
     *
     * @return the newly built stack
     */
    public ItemStack build() {
        ItemStack bookStack = new ItemStack(Items.WRITTEN_BOOK);
        CompoundTag tag = bookStack.getOrCreateTag();
        tag.put("pages", serializePages(pages));
        tag.putString("author", author);
        tag.putString("title", title);
        ItemStackUtil.setLore(bookStack, lore);
        if (titleStyle != null)
            bookStack.setCustomName(new LiteralText(title).fillStyle(titleStyle));
        return bookStack;
    }

    private static ListTag serializePages(List<Page> pages) {
        ListTag pagesListTag = new ListTag();
        for (Page page : pages) {
            LiteralText baseText = new LiteralText("");
            final int lineCount = page.lines.size();
            for (int i = 0; i < lineCount; i++) {
                baseText.append(page.lines.get(i));
                if (i < lineCount - 1)
                    baseText.append(new LiteralText("\n"));
            }
            String contents = Text.Serializer.toJson(baseText);
            pagesListTag.add(StringTag.of(contents));
        }
        return pagesListTag;
    }
}
