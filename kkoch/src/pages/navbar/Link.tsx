import { SelectedPage } from "@/components/types";
import AnchorLink from "react-anchor-link-smooth-scroll"

type Props = {
  page: string;
  selectedPage: SelectedPage;
  setSelectedPage: (value: SelectedPage) => void;
}

function Link({
  page,
  selectedPage,
  setSelectedPage,
}: Props) {
  // 공백 제거, 소문자로 변환
  const lowerCasePage = page.toLowerCase().replace(/ /g, "") as SelectedPage;

  return (
    <AnchorLink
      className={`${selectedPage === lowerCasePage} ? "text-primary-500" : ""
        transition duration-500 hover:text-primary-300
      `}
      href={`#${lowerCasePage}`}
      onClick={() => setSelectedPage(lowerCasePage)}
    >
      {page}
    </AnchorLink>
  )
}

export default Link